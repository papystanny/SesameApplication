package reseau_api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import unique.LockSchedule;
import unique.LoginResponse;
import unique.Pet;
import unique.PetActivity;
import unique.User;

public interface InterfaceServer {

    // Méthode pour la connexion
    @POST("api/login")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("email") String email,
                              @Field("password") String password);

    @POST("api/logout")
    Call<SimpleApiResponse> logout(@Header("Authorization") String authToken);



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

    @GET("api/pets/user/{id}")
    Call<List<Pet>> getPetsByUser(
            @Header("Authorization") String authToken,
            @Path("id") int userId);

    @GET("api/activity/{userID}")
    Call<List<PetActivity>> getPetActivity(
            @Header("Authorization") String authToken,
            @Path("userID") int userId);

    @GET("api/lock_schedules/{userID}")
    Call<List<LockSchedule>> getLockSchedules(
            @Header("Authorization") String authToken,
            @Path("userID") int userId);

    @POST("api/pets")
    Call<Pet> addPet(@Header("Authorization") String authToken,
                     @Field("name") String name,
                     @Field("nickname") String nickname,
                     @Field("img") String img,
                     @Field("type") String type);
}
