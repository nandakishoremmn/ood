package org.rubix.data;

import lombok.*;
import org.rubix.enums.Axis;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Location {
    Axis axis;
    int i, j;
}
