package in.roidsoftware.githubcard;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;
import retrofit.RestAdapter;


public class MainActivity extends AppCompatActivity {

    CardRecyclerView crvViralImages;
    CardArrayRecyclerViewAdapter cardArrayRecyclerViewAdapter;
    ArrayList<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        crvViralImages = (CardRecyclerView) findViewById(R.id.crvViralImages);
        cards = new ArrayList<>();
        cardArrayRecyclerViewAdapter = new CardArrayRecyclerViewAdapter(this, cards);
        crvViralImages.setLayoutManager(new LinearLayoutManager(this));
        if (crvViralImages != null) {
            crvViralImages.setAdapter(cardArrayRecyclerViewAdapter);
        }

        new FetchViralImagesTask().execute();
    }

    public class FetchViralImagesTask extends AsyncTask<Void, Void, List<ViralImage>> {

        @Override
        protected List<ViralImage> doInBackground(Void... voids) {

            ViralImageService viralImageService = new RestAdapter.Builder()
                    .setEndpoint("https://viralfacebookimages.p.mashape.com")
                    .build()
                    .create(ViralImageService.class);
            return viralImageService.getViralImages();
        }

        @Override
        protected void onPostExecute(List<ViralImage> viralImages) {
            super.onPostExecute(viralImages);
            List<Card> cardList = new ArrayList<>();
            for (ViralImage viralImage : viralImages) {
                MaterialLargeImageCard materialLargeImageCard = MaterialLargeImageCard.with(getBaseContext())
                        .setTextOverImage(viralImage.likes_count + " Likes")
                        .useDrawableUrl(viralImage.src_big)
                        .build();
                cardList.add(materialLargeImageCard);
            }
            cardArrayRecyclerViewAdapter.addAll(cardList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
