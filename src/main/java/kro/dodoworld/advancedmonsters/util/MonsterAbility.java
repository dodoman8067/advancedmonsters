package kro.dodoworld.advancedmonsters.util;

public enum MonsterAbility {
    HEALTHY,
    STRONG,
    TANK,
    SPEEDY,
    TELEPORTER,
    INVISIBLE,
    PUNCHY,
    BOOMER,
    FLAMING,
    LASER,
    STORMY,
    VENOMOUS,
    FROZEN,
    LIGHTING,
    REVITALIZE;

    /**
     * @return User-friendly ability name
     */
    @Override
    public String toString(){
        String value = null;
        switch (this){
            case HEALTHY -> value = "Healthy";
            case STRONG -> value = "Strong";
            case TANK -> value = "Tank";
            case SPEEDY -> value = "Speedy";
            case TELEPORTER -> value = "Teleporter";
            case INVISIBLE -> value = "Invisible";
            case PUNCHY -> value = "Punchy";
            case BOOMER -> value = "Boomer";
            case FLAMING -> value = "Flaming";
            case LASER -> value = "Laser";
            case STORMY -> value = "Stormy";
            case VENOMOUS -> value = "Venomous";
            case FROZEN -> value = "Frozen";
            case LIGHTING -> value = "Lighting";
            case REVITALIZE -> value = "Revitalize";
        }
        return value;
    }
}
