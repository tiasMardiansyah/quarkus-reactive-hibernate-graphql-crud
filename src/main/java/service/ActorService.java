package service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import entity.Actor;
import entity.ActorMovieEntity;
import entity.Movie;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import repository.ActorMovieRepository;
import repository.ActorRepository;
import repository.MovieRepository;

@ApplicationScoped
public class ActorService {

    @Inject
    ActorRepository actorRepository;

    @Inject
    MovieRepository movieRepository;

    @Inject
    ActorMovieRepository actorMovieRepository;

    public Uni<List<Actor>> getAllActors() {
        return actorRepository
                .listAll()
                .ifNoItem()
                .after(Duration.ofMillis(10000))
                .fail()
                .onFailure()
                .recoverWithUni(throwable -> Uni.createFrom().item(Collections.emptyList()));
    }

    public Uni<Actor> findByActorId(Long id) {
        return actorRepository.findById(id);
    }

    public Uni<Actor> addMovieToActor(Long movieId, Long actorId) {

        Uni<Actor> actor = actorRepository.findById(actorId);
        Uni<Movie> movie = movieRepository.findByMovieId(actorId);

        Uni<Tuple2<Actor, Movie>> movieActorUni = Uni.combine()
                .all().unis(actor, movie).asTuple();

        return Panache
                .withTransaction(() -> movieActorUni
                        .onItem().ifNotNull()
                        .transform(entity -> {

                            if (entity.getItem2() == null || entity.getItem1() == null) {
                                return null;
                            }
                            return ActorMovieEntity.builder()
                                    .actor(entity.getItem1())
                                    .movie(entity.getItem2()).build();
                        })
                        .onItem().call(actorMovieEntity -> actorMovieRepository.persist(actorMovieEntity))
                        .onItem().transform(actorMovieEntity -> actorMovieEntity.actor));

    }

}
