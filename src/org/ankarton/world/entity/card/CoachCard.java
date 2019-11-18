package org.ankarton.world.entity.card;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.ankarton.util.document.data.card.CoachCardTemplate;
import org.ankarton.util.factory.FactoryManager;
import org.ankarton.world.entity.coach.Coach;

@DatabaseTable(tableName = "coach_cards")
public class CoachCard {

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private short quantity, pos;
    @DatabaseField
    private byte flag;
    @DatabaseField
    private int templateId;
    @DatabaseField(foreign = true, columnName = "coach_id", foreignAutoCreate = true, foreignAutoRefresh = true)
    private Coach coach;

    public CoachCard() {}

    public CoachCard(short quantity, short pos, byte flag, int templateId) {
        this.quantity = quantity;
        this.pos = pos;
        this.flag = flag;
        this.templateId = templateId;
    }

    public long getId() {
        return id;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public short getPos() {
        return pos;
    }

    public void setPos(short pos) {
        this.pos = pos;
    }

    public byte getFlag() {
        return flag;
    }

    public int getTemplateId() {
        return templateId;
    }

    public CoachCardTemplate getTemplate() {
        return FactoryManager.getInstance().getCoachCard().get(this.templateId);
    }
}
