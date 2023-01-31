package eu.pb4.common.economy.api;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.UUID;

@SuppressWarnings({"unused"})
public interface EconomyAccount {
    /**
     * 帐户名称，用于模组的显示
     */
    Text name();

    /**
     * 主要帐户所有者。 如果它是服务器/控制台帐户或没有主要所有者，则返回 Util.NIL_UUID
     */
    UUID owner();

    /**
     * 允许您获取此帐户的标识符。 namespace 应该等于 Provider 的 id。
     * @return
     */
    Identifier id();

    /**
     * 账户余额的原始值
     */
    long balance();

    default Text formattedBalance() {
        return this.currency().formatValueText(this.balance(), false);
    }

    /**
     * 按值增加帐户余额。 只有能全额转账才算成功
     */
    default EconomyTransaction increaseBalance(long value) {
        var t = this.canIncreaseBalance(value);

        if (t.isSuccessful()) {
            this.setBalance(t.finalBalance());
        }
        return t;
    }

    /**
     * 检查账户余额是否可以增加值。 只有可以全额转账才算成功。 所有返回值应与成功操作相同
     */
    EconomyTransaction canIncreaseBalance(long value);

    /**
     * 按值减少帐户余额。 只有能全额转账才算成功
     */
    default EconomyTransaction decreaseBalance(long value) {
        var t = this.canDecreaseBalance(value);

        if (t.isSuccessful()) {
            this.setBalance(t.finalBalance());
        }
        return t;
    }

    /**
     * 检查账户余额是否可以增加值。 只有可以全额转账才算成功。 所有返回值应与成功操作相同
     */
    EconomyTransaction canDecreaseBalance(long value);

    /**
     * 将帐户余额设置为一个值
     */
    void setBalance(long value);

    /**
     * 管理此帐户的提供商
     */
    EconomyProvider provider();

    /**
     * 此帐户使用的货币
     */
    EconomyCurrency currency();

    /**
     * Icon for other mods to use
     */
    default ItemStack accountIcon() {
        return this.provider().icon();
    }
}
