package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.smallrye.graphql.api.Scalar.Date;
import io.smallrye.graphql.api.Scalar.Int;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "dat_gangguan")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Gangguan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nama;

    private java.sql.Date tgl;

    private int status;
}
