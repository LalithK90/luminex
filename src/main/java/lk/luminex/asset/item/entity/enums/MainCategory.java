package lk.luminex.asset.item.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MainCategory {


    CABLE("Cable"),
    BUILDING_MATERIALS("Building Materials"),
    CEMENT("Cement"),
    LIGHT("Light"),
    LADDER("Ladder"),
    ROPE("Rope"),
    HELMET("Helmet");
    /* Database saved name(UID shown name)
    CANNED_FOODS("Canned Foods"),
    */


    private final String mainCategory;
}
