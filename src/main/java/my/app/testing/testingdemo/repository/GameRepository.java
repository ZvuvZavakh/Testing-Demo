package my.app.testing.testingdemo.repository;

import my.app.testing.testingdemo.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query(value = "select g from Game g where g.name = ?1")
    List<Game> selectGamesByName(String name);

    @Query("select case when count(g) > 0 then true else false end from Game g where g.name = ?1")
    Boolean checkIfGameExistsByName(String name);
}
