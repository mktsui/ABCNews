package exam.freelancer.mktsui.movlancer.database;

import java.util.List;

public interface AsyncResult {
    void asyncCompleted(List<MovieEntity> results);
}
