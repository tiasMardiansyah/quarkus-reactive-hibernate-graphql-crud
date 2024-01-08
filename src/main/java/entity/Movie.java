// package entity;

// import io.quarkus.panache.common.Sort;
// import io.smallrye.mutiny.Uni;
// import io.smallrye.mutiny.tuples.Tuple2;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;

// import jakarta.persistence.Cacheable;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import java.time.Duration;
// import java.time.LocalDate;
// import java.util.Collections;
// import java.util.List;

// @Entity
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// @Cacheable
// @Getter
// public class Movie {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     public Long id;

//     public String title;
//     public String director;
//     public LocalDate releaseDate;

// }
