package ru.ntdv.proicis.buisness.robot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ntdv.proicis.constant.Stage;
import ru.ntdv.proicis.crud.model.SecretCode;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public final
class RobotStateManager {
private static RobotStateManager INSTANCE;
private final Map<Robot, RobotState> state;

private
RobotStateManager() {
    final EnumMap<Robot, RobotState> map = new EnumMap<>(Robot.class);
    map.put(Robot.STAGEINFO_REGISTERING_END, new RobotState());
    map.put(Robot.SECRETCODE_DELETE_EXPIRED, new RobotState());

    if (map.size() != Robot.values().length) {
        throw new NullPointerException("Robot state is null.");
    } else {
        state = Map.copyOf(map);
    }
}

public static
RobotStateManager getInstance() {
    if (INSTANCE == null) INSTANCE = new RobotStateManager();
    return INSTANCE;
}

public
boolean isEnabled(final Robot robot) {
    return this.state.get(robot).isEnabled();
}

public
<T> void enableFor(final T obj) {
    setEnabledFor(obj, true);
}

private
<T> void setEnabledFor(final T obj, final boolean enabled) {
    if (obj instanceof final Stage stage) {
        if (Objects.requireNonNull(stage) == Stage.Registering) {
            setEnabledFor(Robot.STAGEINFO_REGISTERING_END, enabled);
        }
    } else if (obj instanceof SecretCode) {
        setEnabledFor(Robot.SECRETCODE_DELETE_EXPIRED, enabled);
    }
}

private
void setEnabledFor(final Robot robot, final boolean enabled) {
    this.state.get(robot).setEnabled(enabled);
}

public
<T> void disableFor(final T obj) {
    setEnabledFor(obj, false);
}

public
void disable(final Robot robot) {
    this.state.get(robot).setEnabled(false);
}

public
void enable(final Robot robot) {
    this.state.get(robot).setEnabled(true);
}

enum Robot {
    STAGEINFO_REGISTERING_END,
    SECRETCODE_DELETE_EXPIRED,
}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public static
class RobotState {
    //private final Robots robot;
    private boolean enabled = true;
}
}
