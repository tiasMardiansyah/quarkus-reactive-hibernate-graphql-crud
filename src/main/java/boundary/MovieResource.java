package boundary;

import control.exception.AlreadyExistingException;
import entity.ActorMovieEntity;
import entity.Movie;
import entity.dto.ActorDTO;
import entity.dto.MovieDTO;
import io.smallrye.mutiny.Uni;
import service.ActorMovieService;
import service.ActorService;
import service.MovieService;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;

import java.util.List;

import javax.inject.Inject;

@GraphQLApi
public class MovieResource {

    @Inject
    MovieService movieService;

    @Inject
    ActorMovieService actorMovieService;

    @Query("allMovies")
    @Description("Get all Movies")
    public Uni<List<MovieDTO>> getAllMovies() {
        return movieService.getAllMovies().onItem().transform(MovieDTO::from);
    }

    @Query
    @Description("Get a movie")
    public Uni<MovieDTO> getMovie(@Name("movieId") long id) {
        return movieService.findByMovieId(id).onItem().transform(MovieDTO::from);
    }

    public Uni<List<ActorDTO>> actors(@Source(name = "MovieResponse") MovieDTO movie) {
        return actorMovieService.getActorsByMovieQuery(movie.id)
                .onItem()
                .transform(actorMovieEntity ->
                        ActorDTO.from(actorMovieEntity.actor))
                .collect().asList();
    }

    @Mutation
    @Description("Create a movie")
    public Uni<MovieDTO> createMovie(Movie movie) {
        return movieService.addMovie(movie).onItem().transform(MovieDTO::from);
    }

    @Mutation
    @Description("Update a movie")
    public Uni<MovieDTO> updateMovie(@Name("movieId") long id, Movie movie) {
        return movieService.updateMovie(id, movie).onItem().transform(MovieDTO::from);
    }

    @Mutation
    @Description("Delete a movie")
    public Uni<Boolean> deleteMovie(@Name("movieId") long id) {
        return movieService.deleteMovie(id);
    }

    @Mutation
    @Description("Add actor to movie")
    public Uni<MovieDTO> addActorToMovie(@Name("movieId") long movieId, @Name("actorId") long actorId) {
        return movieService.addActorToMovie(movieId, actorId).onItem().transform(MovieDTO::from).onFailure().
                transform(throwable -> new AlreadyExistingException("movieId: " + movieId + " and actorId: " + actorId));
    }

}