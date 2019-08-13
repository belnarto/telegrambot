package by.belnarto.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {

    @PositiveOrZero
    private Long id;

    @NotBlank(message = "City name is mandatory")
    @Length(max = 255, message = "City name should less than 256 characters")
    private String cityName;

    @NotBlank(message = "City info is mandatory")
    @Length(max = 255, message = "City info should less than 256 characters")
    private String cityInfo;

    @Override
    public String toString() {
        return cityName + ": "
                + cityInfo;
    }

}
