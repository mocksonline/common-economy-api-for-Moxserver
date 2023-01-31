package eu.pb4.common.economy.api;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * A currency of a mod
 */
public interface EconomyCurrency {
    /**
     * 货币的名称，被模组用来显示信息
     */
    Text name();

    /**
     * 允许您获取此实例的标识符。 namespace 应该等于 Provider 的 id。
     */
    Identifier id();

    default Text formatValueText(long value, boolean precise) {
        return Text.literal(this.formatValue(value, precise));
    }

    /**
     * 显示/配置存储的格式值
     *
     * @param value raw value
     * @param precise whatever it should be precise (down to lowest values)
     * @return balance formatted as string
     */
    String formatValue(long value, boolean precise);

    /**
     * Parses string input to raw value.
     * This method should be able to parse output of formatValue with precise = true
     *
     * @param value String value
     * @return raw amount
     * @throws NumberFormatException
     */
    long parseValue(String value) throws NumberFormatException;

    /**
     * Provider managing this currency
     */
    EconomyProvider provider();

    /**
     * Icons for other mods to use in guis
     */
    default ItemStack icon() {
        return Items.SUNFLOWER.getDefaultStack();
    }
}
