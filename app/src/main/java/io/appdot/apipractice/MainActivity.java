package io.appdot.apipractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText isbnInput = findViewById(R.id.isbnInput);
        final TextView textView = (TextView) findViewById(R.id.textView);
        final Button button = findViewById(R.id.button);

        final Button viewAllButton = findViewById(R.id.viewAllButton);
        final TextView allBooks = findViewById(R.id.allBooks);

        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://apiss-practice.herokuapp.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        final BookService service = retrofit.create(BookService.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book(isbnInput.getText().toString());
                Call<Book> createCall = service.create(book);
                createCall.enqueue(new Callback<Book>(){

                    @Override
                    public void onResponse(Call<Book> call, Response<Book> resp) {
                        Book newBook = resp.body();
                        textView.setText("Created Book with ISBN: " + newBook.isbn);

                    }

                    @Override
                    public void onFailure(Call<Book> call, Throwable t) {
                        t.printStackTrace();
                        textView.setText(t.getMessage());
                    }
                });
            }
        });

        viewAllButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Call<List<Book>> createCall = service.all();
                createCall.enqueue(new Callback<List<Book>>() {
                    @Override
                    public void onResponse(Call<List<Book>> call, Response<List<Book>> resp) {
                        allBooks.setText("ALL BOOKS by ISBN: \n");

                        for(Book b :resp.body()){
                            allBooks.append(b.isbn + "\n");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Book>> call, Throwable t) {
                            t.printStackTrace();
                            allBooks.setText(t.getMessage());

                    }
                });

                }

        }
        );





        //first practice for apis
        /*

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apiss-practice.herokuapp.com/")
                .build();
        final HerokuService service = retrofit.create(HerokuService.class);


        button.setOnClickListener( new View.OnClickListener()
           {@Override
           public void onClick(View v) {
               Call<ResponseBody> call = service.hello();
               call.enqueue(new Callback<ResponseBody>() {
                   @Override
                   public void onResponse(Call<ResponseBody> _,
                                          Response<ResponseBody> response) {
                       try {
                           textView.setText(response.body().string());
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                   }

                   @Override
                   public void onFailure(Call<ResponseBody> _, Throwable t) {
                        t.printStackTrace();
                        textView.setText(t.getMessage());


                   }
               });
                  }
            }
        );
*/
    }
}
