package service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import entity.ActorMovieEntity;
import io.smallrye.mutiny.Multi;
import repository.ActorMovieRepository;

@ApplicationScoped
public class ActorMovieService {
    
    @Inject
    ActorMovieRepository actorMovieRepository;

    public Multi<ActorMovieEntity> getActorsByMovieQuery(Long movieId) {
        return actorMovieRepository.getActorsByMovieQuery(movieId);
    }

    public Multi<ActorMovieEntity> getMoviesByActorQuery(Long actorId) {
        return actorMovieRepository.getMoviesByActorQuery(actorId);
    }
}
