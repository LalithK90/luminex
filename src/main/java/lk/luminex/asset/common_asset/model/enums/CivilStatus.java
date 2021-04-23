package lk.luminex.asset.common_asset.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CivilStatus {
    MARRIED("Married"),
    UNMARRIED("Unmarried");

    private final String civilStatus;
}
