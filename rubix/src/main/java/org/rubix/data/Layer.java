package org.rubix.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.rubix.enums.Axis;

@Data
@AllArgsConstructor
public class Layer {
    int index;
    Axis axis;
}
