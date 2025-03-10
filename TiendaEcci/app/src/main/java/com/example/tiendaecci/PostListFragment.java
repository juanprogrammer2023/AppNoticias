package com.example.tiendaecci;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostListFragment extends Fragment {

    private static final String ARG_CATEGORY_ID = "category_id";
    private int categoryId; // ID de la categoría seleccionada
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList = new ArrayList<>();
    private TextView emptyView;

    public PostListFragment() {
        // Constructor vacío
    }

    // Método para crear una instancia del fragmento con la categoría específica
    public static PostListFragment newInstance(int categoryId) {
        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(ARG_CATEGORY_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

        // Referencias a los elementos de la vista
        recyclerView = view.findViewById(R.id.recycler_view);
        emptyView = view.findViewById(R.id.empty_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postAdapter = new PostAdapter(postList, getContext());
        recyclerView.setAdapter(postAdapter);

        ImageView categoryImage = view.findViewById(R.id.categoryImage);

        // Asigna la imagen de acuerdo con el ID de la categoría
        switch (categoryId) {
            case 1:
                categoryImage.setImageResource(R.drawable.sports); // Imagen para deportes
                break;
            case 2:
                categoryImage.setImageResource(R.drawable.noticias); // Imagen para noticias
                break;
            case 3:
                categoryImage.setImageResource(R.drawable.technology); // Imagen para tecnología
                break;
            case 4:
                categoryImage.setImageResource(R.drawable.aliments); // Imagen para alimentos
                break;
            default:
                categoryImage.setImageResource(R.drawable.default_image); // Imagen por defecto
                break;
        }

        // Cargar los posts
        loadPostsByCategory();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPostsByCategory(); // Recargar los posts al reanudar
    }

    private void loadPostsByCategory() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        apiService.getPostsByCategory(categoryId).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    postList.clear(); // Limpia la lista existente
                    postList.addAll(response.body()); // Agrega los nuevos datos
                    postAdapter.notifyDataSetChanged(); // Notifica al adaptador sobre los cambios
                } else {
                    Log.e("PostListFragment", "Error al cargar los posts: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("PostListFragment", "Error de conexión: " + t.getMessage());
            }
        });
    }


    private void showEmptyView() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }



}
