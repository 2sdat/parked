package dev.aidaco.parked.Model;

import androidx.room.TypeConverter;

public class Enums {

    public enum UserType {
        BASIC(0),
        MANAGER(1),
        ADMIN(2);

        private final int typeCode;

        UserType(int typeCode) {
            this.typeCode = typeCode;
        }

        public int getTypeCode() {
            return this.typeCode;
        }

        public static UserType fromTypeCode(int typeCode) {
            if (typeCode == BASIC.getTypeCode()) {
                return BASIC;
            } else if (typeCode == ADMIN.getTypeCode()) {
                return ADMIN;
            } else if (typeCode == MANAGER.getTypeCode()) {
                return MANAGER;
            } else {
                return null;
            }
        }
    }

    public enum VehicleType {
        CAR(0, "Car"),
        TRUCK(1, "Truck"),
        MOTORCYCLE(2, "Motorcycle");

        private final int typeCode;
        private final String name;

        VehicleType(int typeCode, String name) {
            this.typeCode = typeCode;
            this.name = name;
        }

        public int getTypeCode() {
            return this.typeCode;
        }

        public String getName() {
            return this.name;
        }

        public static VehicleType fromTypeCode(int typeCode) {
            if (typeCode == CAR.getTypeCode()) {
                return CAR;
            } else if (typeCode == TRUCK.getTypeCode()) {
                return TRUCK;
            } else if (typeCode == MOTORCYCLE.getTypeCode()) {
                return MOTORCYCLE;
            } else {
                return null;
            }
        }
    }

    public enum BillingType {
        HOURLY(0),
        EARLY_BIRD(1),
        OVERNIGHT(2);

        private final int typeCode;

        BillingType(int typeCode) {
            this.typeCode = typeCode;
        }

        public int getTypeCode() {
            return this.typeCode;
        }

        public static BillingType fromTypeCode(int typeCode) {
            if (typeCode == HOURLY.getTypeCode()) {
                return HOURLY;
            } else if (typeCode == EARLY_BIRD.getTypeCode()) {
                return EARLY_BIRD;
            } else if (typeCode == OVERNIGHT.getTypeCode()) {
                return OVERNIGHT;
            } else {
                return null;
            }
        }
    }

    public enum State {
        AL(0, "Alabama"),
        AK(1, "Alaska"),
        AZ(2, "Arizona"),
        AR(3, "Arkansas"),
        CA(4, "California"),
        CO(5, "Colorado"),
        CT(6, "Connecticut"),
        DE(7, "Delaware"),
        FL(8, "Florida"),
        GA(9, "Georgia"),
        HI(10, "Hawaii"),
        ID(11, "Idaho"),
        IL(12, "Illinois"),
        IN(13, "Indiana"),
        IA(14, "Iowa"),
        KS(15, "Kansas"),
        KY(16, "Kentucky"),
        LA(17, "Louisiana"),
        ME(18, "Maine"),
        MD(19, "Maryland"),
        MA(20, "Massachusetts"),
        MI(21, "Michigan"),
        MN(22, "Minnesota"),
        MS(23, "Mississippi"),
        MO(24, "Missouri"),
        MT(25, "Montana"),
        NE(26, "Nebraska"),
        NV(27, "Nevada"),
        NH(28, "New Hampshire"),
        NJ(29, "New Jersey"),
        NM(30, "New Mexico"),
        NY(31, "New York"),
        NC(32, "North Carolina"),
        ND(33, "North Dakota"),
        OH(34, "Ohio"),
        OK(35, "Oklahoma"),
        OR(36, "Oregon"),
        PA(37, "Pennsylvania"),
        RI(38, "Rhode Island"),
        SC(39, "South Carolina"),
        SD(40, "South Dakota"),
        TN(41, "Tennessee"),
        TX(42, "Texas"),
        UT(43, "Utah"),
        VT(44, "Vermont"),
        VA(45, "Virginia"),
        WA(46, "Washington"),
        WV(47, "West Virginia"),
        WI(48, "Wisconsin"),
        WY(49, "Wyoming");

        private final int typeCode;
        private final String fullName;

        State(int typeCode, String fullName) {
            this.typeCode = typeCode;
            this.fullName = fullName;
        }

        public int getTypeCode() {
            return this.typeCode;
        }

        public static State fromTypeCode(int typeCode) {
            for (State state : State.values()) {
                if (typeCode == state.getTypeCode()) {
                    return state;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.fullName;
        }
    }

    @TypeConverter
    public static UserType toUserType(int typeCode) {
        return UserType.fromTypeCode(typeCode);
    }

    @TypeConverter
    public static int fromUserType(UserType userType) {
        return userType.getTypeCode();
    }

    @TypeConverter
    public static VehicleType toVehicleType(int typeCode) {
        return VehicleType.fromTypeCode(typeCode);
    }

    @TypeConverter
    public static int fromVehicleType(VehicleType vehicleType) {
        return vehicleType.getTypeCode();
    }

    @TypeConverter
    public static BillingType toBillingType(int typeCode) {
        return BillingType.fromTypeCode(typeCode);
    }

    @TypeConverter
    public static int fromBillingType(BillingType billingType) {
        return billingType.getTypeCode();
    }

    @TypeConverter
    public static State toState(int typeCode) {
        return State.fromTypeCode(typeCode);
    }

    @TypeConverter
    public static int fromState(State state) {
        return state.getTypeCode();
    }
}
