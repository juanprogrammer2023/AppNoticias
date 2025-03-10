package com.example.tiendaecci;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/api/users/register")
    Call<Void> registerUser(@Body UserRequest userRequest);

    // Endpoint para el login
    @POST("api/users/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("api/posts/get-by-category")
    Call<List<Post>> getPostsByCategory(@Query("categoria_id") int categoriaId);

    @POST("/api/posts/create")
    Call<PostResponse> createPost(@Body PostRequest postRequest);

    @GET("/api/comments/{postId}")
    Call<List<Comment>> getCommentsByPostId(@Path("postId") int postId);

    @POST("api/comments")
    Call<Comment> createComment(@Body Comment comment);

    @DELETE("api/comments/{id}")
    Call<Void> deleteComment(@Path("id") int id);

    @GET("/ip")
    Call<ServerIpResponse> getServerIp();

}
