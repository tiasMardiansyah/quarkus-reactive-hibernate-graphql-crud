// package entity;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;

// import jakarta.persistence.Cacheable;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;

// @Entity
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// @Cacheable
// @Getter
// public class Actor {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     public Long id;

//     public String name;

//     public String toString() {
//         return this.getClass().getSimpleName() + "<" + this.id + ">";
//     }
// }
