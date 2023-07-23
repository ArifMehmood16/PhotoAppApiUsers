package com.appsdeveloper.photoapp.api.users.shared;

import com.appsdeveloper.photoapp.api.users.ui.model.AlbumResponseModel;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {
    private static final long serialVersionUID = -7861565809686013550L;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private List<AlbumResponseModel> albums;
}
