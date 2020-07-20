package lk.luminex.asset.item.brand.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Brand" )
public class Brand {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;


    @Size( min = 3, message = "Your name cannot be accepted" )
    private String name;

/*    @OneToMany(mappedBy = "brand",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items;*/
}
