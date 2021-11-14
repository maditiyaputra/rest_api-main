package id.ac.ub.restapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.ac.ub.restapi.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<Buku> listBuku = new ArrayList<>();
    EditText et_judul, et_deskripsi;
    Button et_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BukuAdapter adapter=new BukuAdapter(this,listBuku);
        binding.recyclerView.setAdapter(adapter);
        PerpustakaanService perpustakaanService = RetrofitClient.getClient().create(PerpustakaanService.class);
        Call<List<Buku>> listRequest = perpustakaanService.listBuku();
        listRequest.enqueue(new Callback<List<Buku>>() {
            @Override
            public void onResponse(Call<List<Buku>> call, Response<List<Buku>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    List<Buku> list = response.body();
                    Log.d("success", "list " + list.size());
                    listBuku.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("errt", "" + response.errorBody());
                    Toast.makeText(MainActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Buku>> call, Throwable t) {
                Log.d("DataModel", "" + t.getMessage());
                Toast.makeText(getApplicationContext(), "Error : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        et_judul = findViewById(R.id.et_judul);
        et_deskripsi = findViewById(R.id.et_deskripsi);
        et_button = findViewById(R.id.et_button);

        et_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Buku> tambah = perpustakaanService.create(et_judul.getText().toString(),et_deskripsi.getText().toString());
                tambah.enqueue(new Callback<Buku>() {
                    @Override
                    public void onResponse(Call<Buku> call, Response<Buku> response) {
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Buku> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error : " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}