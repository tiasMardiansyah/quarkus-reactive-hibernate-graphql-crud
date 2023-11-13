package boundary;

import control.exception.AlreadyExistingException;
import entity.ActorMovieEntity;
import entity.dto.ActorDTO;
import entity.dto.MovieDTO;
import io.smallrye.mutiny.Uni;
import repository.MovieRepository;
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
public class ActorResource {

    @Inject
    ActorService actorService;

    @Inject
    MovieService movieService;

    @Inject
    ActorMovieService actorMovieService;

    @Query("allActors")
    @Description("Get all Actors")
    public Uni<List<ActorDTO>> getAllActors() {
        return actorService.getAllActors()
                .onItem().transform(ActorDTO::from);
    }

    @Query
    @Description("Get an actor")
    public Uni<ActorDTO> getActor(@Name("actorId") long id) {
        return actorService.findByActorId(id).onItem().transform(ActorDTO::from);
    }

    public Uni<List<MovieDTO>> movies(@Source(name = "ActorResponse") ActorDTO actor) {
        return actorMovieService.getMoviesByActorQuery(actor.id).onItem().transform(actorMovieEntity ->
                actorMovieEntity.movie).collect().asList().onItem().transform(MovieDTO::from);
    }

    @Mutation
    @Description("Add movie to actor")
    public Uni<ActorDTO> addMovieToActor(@Name("movieId") long movieId, @Name("actorId") long actorId) {
        return actorService.addMovieToActor(movieId, actorId).onItem().transform(ActorDTO::from).onFailure().
                transform(throwable -> new AlreadyExistingException("movieId: " + movieId + " and actorId: " + actorId));
    }
}
