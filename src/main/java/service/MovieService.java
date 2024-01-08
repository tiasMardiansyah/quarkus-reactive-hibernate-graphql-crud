// package service;

// import java.time.Duration;
// import java.util.Collections;
// import java.util.List;

// import javax.enterprise.context.ApplicationScoped;
// import javax.inject.Inject;

// import entity.Actor;
// import entity.ActorMovieEntity;
// import entity.Movie;
// import io.quarkus.hibernate.reactive.panache.Panache;
// import io.quarkus.panache.common.Sort;
// import io.smallrye.mutiny.Uni;
// import io.smallrye.mutiny.tuples.Tuple2;
// import repository.ActorMovieRepository;
// import repository.ActorRepository;
// import repository.MovieRepository;

// @ApplicationScoped
// public class MovieService {
    
//     @Inject 
//     MovieRepository movieRepository;

//     @Inject 
//     ActorRepository actorRepository;

//     @Inject
//     ActorMovieRepository actorMovieRepository;

//     public Uni<List<Movie>> getAllMovies() {
//         return movieRepository
//                 .listAll(Sort.by("releaseDate"))
//                 .ifNoItem()
//                 .after(Duration.ofMillis(10000))
//                 .fail()
//                 .onFailure()
//                 .recoverWithUni(throwable -> Uni.createFrom().item(Collections.emptyList()));
//     }

//     public Uni<Movie> findByMovieId(Long id) {
//         return movieRepository.findByMovieId(id);
//     }

//     public Uni<Movie> updateMovie(Long id, Movie movie) {
//         return Panache
//                 .withTransaction(() -> movieRepository.findByMovieId(id)
//                         .onItem().ifNotNull()
//                         .transform(entity -> {
//                             entity.title = movie.title;
//                             return entity;
//                         })
//                         .onFailure().recoverWithNull());
//     }

//     public Uni<Movie> addMovie(Movie movie) {
//         return Panache
//                 .withTransaction(() -> movieRepository.persist(movie))
//                 .replaceWith(movie)
//                 .ifNoItem()
//                 .after(Duration.ofMillis(10000))
//                 .fail()
//                 .onFailure()
//                 .transform(t -> new IllegalStateException(t));
//     }

//     public Uni<Boolean> deleteMovie(Long id) {
//         return Panache.withTransaction(() -> movieRepository.deleteById(id));
//     }

//     public Uni<Movie> addActorToMovie(Long movieId, Long actorId) {

//         Uni<Movie> movie = movieRepository.findById(movieId);
//         Uni<Actor> actor = actorRepository.findById(actorId);

//         Uni<Tuple2<Actor, Movie>> movieActorUni = Uni.combine()
//                 .all().unis(actor, movie).asTuple();

//         return Panache
//             .withTransaction(() -> movieActorUni
//                     .onItem().ifNotNull()
//                     .transform(entity -> {

//                         if (entity.getItem2() == null || entity.getItem1() == null) {
//                             return null;
//                         }
//                         return ActorMovieEntity.builder()
//                                 .actor(entity.getItem1())
//                                 .movie(entity.getItem2()).build();

//                     })
//                     .onItem().call(actorMovieEntity -> actorMovieRepository.persist(actorMovieEntity))
//                     .onItem().transform(actorMovieEntity -> actorMovieEntity.movie));
//     }
// }
