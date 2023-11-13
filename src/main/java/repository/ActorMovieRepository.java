package repository;

import javax.enterprise.context.ApplicationScoped;

import entity.ActorMovieEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class ActorMovieRepository implements PanacheRepositoryBase<ActorMovieEntity, Long> {
    
    public Multi<ActorMovieEntity> getActorsByMovieQuery(Long movieId) {
        return stream("#ActorMovieEntity.getByMovieId", movieId);
    }

    public Multi<ActorMovieEntity> getMoviesByActorQuery(Long actorId) {
        return stream("#ActorMovieEntity.getByActorId", actorId);
    }

}
