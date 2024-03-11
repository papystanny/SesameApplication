package reseau_api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import unique.LoginResponse;
import unique.User;

public interface InterfaceServer {

    // Méthode pour la connexion
    @POST("api/login")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("email") String email,
                              @Field("password") String password);

    @POST("api/send-reset-code")
    @FormUrlEncoded
    Call<User> send_reset_code(@Field("email") String email);

    @POST("api/changePassword")
    @FormUrlEncoded
    Call<SimpleApiResponse> changePassword(@Field("email") String email,
                                           @Field("password") String password,
                                           @Field("password_confirmation") String password_confirmation);

    @POST("api/verify-Reset-Token")
    @FormUrlEncoded
    Call<SimpleApiResponse> verify_Reset_Token(@Field("email") String email,
                                               @Field("token") String token);

    @POST("api/register")
    @FormUrlEncoded
    Call<LoginResponse> register(@Field("firstname") String firstname,
                                 @Field("lastname") String lastname,
                                 @Field("phone") String phone,
                                 @Field("email") String email,
                                 @Field("password") String password);

}
