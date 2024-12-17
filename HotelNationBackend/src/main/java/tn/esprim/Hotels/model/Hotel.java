package tn.esprim.Hotels.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "hotels")
public class Hotel {
    @Id
    private String id;
    
    @Field("Name")
    private String name;
    
    @Field("Price")
    private String price;
    
    @Field("Location")
    private String location;
    
    @Field("Stars")
    private String stars;
    
    @Field("Type")
    private String type;
    
    @Field("Link")
    private String link;
} 