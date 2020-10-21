package cn.dyg.optional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A container object which may or may not contain a non-null value.
 * If a value is present, {@code isPresent()} will return {@code true} and
 * {@code get()} will return the value.
 *
 * <p>Additional methods that depend on the presence or absence of a contained
 * value are provided, such as {@link #orElse(java.lang.Object) orElse()}
 * (return a default value if value not present) and
 * {@link #ifPresent(java.util.function.Consumer) ifPresent()} (execute a block
 * of code if the value is present).
 *
 * <p>This is a <a href="../lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code Optional} may have unpredictable results and should be avoided.
 *
 * @since 1.8
 */
public final class Optional<T> {
    /**
     * 一个公共空Optional实例
     */
    private static final Optional<?> EMPTY = new Optional<>();

    /**
     * 如果非空，则value值存在，否则value值为null
     */
    private final T value;

    /**
     * 构造方法，构造一个value为空的Optional对象，是一个私有构造方法
     */
    private Optional() {
        this.value = null;
    }

    /**
     * 返回一个空的Optional对象
     */
    public static<T> Optional<T> empty() {
        @SuppressWarnings("unchecked")
        Optional<T> t = (Optional<T>) EMPTY;
        return t;
    }

    /**
     * 有参构造方法
     * 如果value为null,则抛出空指针异常,否则将值赋给Optional对象的value属性
     * 也是一个私有构造函数
     */
    private Optional(T value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * 调用有参构造方法，返回一个value为入参的Optional对象
     * 若入参为null,会抛出空指针异常
     */
    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    /**
     * 返回一个Optional对象，value值为入参
     * 允许value值为null,若为null则调用empty()进行返回
     */
    public static <T> Optional<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    /**
     * 获取Optional对象的value值
     * 若为空，则抛异常NoSuchElementException
     */
    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * 判断value值是否为null
     * 为null,则返回false,否则返回true
     */
    public boolean isPresent() {
        return value != null;
    }

    /**
     * 如果value不为null,执行consumer的accept方法进行相应逻辑
     * 否则,不做任何操作
     */
    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null)
            consumer.accept(value);
    }

    /**
     * filter方法的参数是内置函数式接口-断言接口Predicate的实现类
     * 首先判断实现类是否为空,若空则抛异常
     * 然后判断value是否为null,为null时直接返回空的Optional
     * 不为null是判断是否满足要求predicate的test条件，满足则返回其本身,否则返回空的Optional
     */
    public Optional<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent())
            return this;
        else
            return predicate.test(value) ? this : empty();
    }

    /**
     * map方法
     * map的参数是Function接口的实现类
     * 当value为null时,返回空的Optional
     * 不为null时,调用参数的apply方法并返回ofNullable(结果)的Optional对象
     */
    public<U> Optional<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return Optional.ofNullable(mapper.apply(value));
        }
    }

    /**
     *
     */
    public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return Objects.requireNonNull(mapper.apply(value));
        }
    }

    /**
     * 如果optional对象保存的值不是null，则返回原来的值，否则返回value
     */
    public T orElse(T other) {
        return value != null ? value : other;
    }

    /**
     * 功能与orElse一样，只不过orElseGet参数是一个Supplier的实现类
     */
    public T orElseGet(Supplier<? extends T> other) {
        return value != null ? value : other.get();
    }

    /**
     * 值不存在则抛出异常，存在则什么不做
     */
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    /**
     * Indicates whether some other object is "equal to" this Optional. The
     * other object is considered equal if:
     * <ul>
     * <li>it is also an {@code Optional} and;
     * <li>both instances have no value present or;
     * <li>the present values are "equal to" each other via {@code equals()}.
     * </ul>
     *
     * @param obj an object to be tested for equality
     * @return {code true} if the other object is "equal to" this object
     * otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Optional)) {
            return false;
        }

        Optional<?> other = (Optional<?>) obj;
        return Objects.equals(value, other.value);
    }

    /**
     * Returns the hash code value of the present value, if any, or 0 (zero) if
     * no value is present.
     *
     * @return hash code value of the present value or 0 if no value is present
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    /**
     * Returns a non-empty string representation of this Optional suitable for
     * debugging. The exact presentation format is unspecified and may vary
     * between implementations and versions.
     *
     * @implSpec If a value is present the result must include its string
     * representation in the result. Empty and present Optionals must be
     * unambiguously differentiable.
     *
     * @return the string representation of this instance
     */
    @Override
    public String toString() {
        return value != null
                ? String.format("Optional[%s]", value)
                : "Optional.empty";
    }
}

