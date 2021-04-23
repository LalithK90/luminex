package lk.luminex.asset.item.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MainCategory {

    CONSTRUCTION_ACCESSORIES("Construction Accessories"),
    SAFETY_MATERIALS("Safety Material");

   /* CABLE("Cable"),
    CABLE_TIE("Cable Tie"),
    BUILDING_MATERIALS("Building Materials"),
    GRIP("Grip"),
    CEMENT("Cement"),
    LIGHT("Light"),
    LADDER("Ladder"),
    ROPE("Rope"),
    SAFETY_BELT("Safety  Belt"),
    GALVANISE_BRACKET("Galvanise Bracket"),
    CLAMP("Clamp"),
    HELMET("Helmet");*/

    /* Database saved name(UID shown name)
    CANNED_FOODS("Canned Foods"),
    */

    private final String mainCategory;
}
