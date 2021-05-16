package org.smart.home.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.smart.home.enums.ExecutionStatus;

@Data
@AllArgsConstructor
public class CommandStatus {
    private ExecutionStatus status;
    private String message;
}
