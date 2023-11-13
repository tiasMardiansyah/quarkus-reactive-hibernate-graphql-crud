package repository;

import javax.enterprise.context.ApplicationScoped;

import entity.Movie;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class MovieRepository implements PanacheRepositoryBase<Movie, Long> {
    
    public Uni<Movie> findByMovieId(Long id) {
        return findById(id);
    }
}
