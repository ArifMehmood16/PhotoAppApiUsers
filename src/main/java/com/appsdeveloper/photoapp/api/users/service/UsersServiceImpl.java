package com.appsdeveloper.photoapp.api.users.service;

import com.appsdeveloper.photoapp.api.users.data.AlbumsServiceClient;
import com.appsdeveloper.photoapp.api.users.data.UserEntity;
import com.appsdeveloper.photoapp.api.users.data.UsersRepository;
import com.appsdeveloper.photoapp.api.users.shared.UserDto;
import com.appsdeveloper.photoapp.api.users.ui.model.AlbumResponseModel;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    Environment environment;
    @Autowired
    AlbumsServiceClient albumsServiceClient;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        usersRepository.save(userEntity);
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = usersRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto getUserDetailsByUserId(String userId) {
        UserEntity userEntity = usersRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        String albumsUrl = String.format(environment.getProperty("albums.url"), userId);
        ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate.exchange(albumsUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
                });
        List<AlbumResponseModel> albumsList = albumsListResponse.getBody();


//        List<AlbumResponseModel> albumsList = null;
//        try {
//            albumsList = albumsServiceClient.getAlbums(userId);
//        } catch (FeignException e) {
//            logger.error(e.getLocalizedMessage());
//        }

//        logger.debug("Before calling albums microservice");
//        albumsList = albumsServiceClient.getAlbums(userId);
//        logger.debug("After calling albums microservice");


        userDto.setAlbums(albumsList);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findByEmail(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                true, true, true, true, new ArrayList<>());

    }
}
