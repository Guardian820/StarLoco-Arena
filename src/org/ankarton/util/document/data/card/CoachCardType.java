package org.ankarton.util.document.data.card;

public enum CoachCardType {

    PANT(2, "Culotte", new short[] { 5 }),
    HAIRS(3, "Coiffure", new short[] { 2 }),
    TATOO(4, "Tatouages", new short[] { 1 }),
    ARMBAND(5, "Brassard", new short[] { 4, 12 }),
    SHOES(6, "Bottes", new short[] { 10 }),
    SHOULDERPAD(7, "Epaulette", new short[] { 3, 13 }),
    CLOAK(8, "Cape", new short[] { 8 }),
    TROUSERS(9, "Pantalon", new short[] { 6 }),
    SHIR(10, "Chemise", new short[] { 11 }),
    HAT(11, "Chapeau", new short[1]),
    STAFF(12, "Bâton", new short[] { 7 }),
    PET(13, "Familier", new short[] { 9 });

    private final int id;
    private final String name;
    private final short[] positions;

    CoachCardType(int id, String name, short[] positions) {
        this.id = id;
        this.name = name;
        this.positions = positions;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public short[] getPositions() { return this.positions; }

    public static CoachCardType getFromId(int typeId) {
        CoachCardType[] array;
        int j = (array = values()).length;
        for (int i = 0; i < j; i++) {
            CoachCardType type = array[i];
            if (type.getId() == typeId)
                return type;
        }
        return null;
    }
}