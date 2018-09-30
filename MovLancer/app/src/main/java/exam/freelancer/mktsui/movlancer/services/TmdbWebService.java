package exam.freelancer.mktsui.movlancer.services;

import exam.freelancer.mktsui.movlancer.model.TmdbReturnModel;
import exam.freelancer.mktsui.movlancer.utilities.Constants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbWebService {

    @GET(Constants.API_FFED)
    Call<TmdbReturnModel> getMovies(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page);
}
