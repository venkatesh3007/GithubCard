package in.roidsoftware.githubcard;

import java.util.List;

import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by venkatesh on 28/5/15.
 */
public interface ViralImageService {

    @Headers({
            "X-Mashape-Key: 6xVaQV3xZmPiMUbbxVJ2kyGujfaVqsar",
            "Content-Type: application/x-www-form-urlencoded",
            "Accept: application/json"
    })
    @POST("/dl")
    List<ViralImage> getViralImages();
}
