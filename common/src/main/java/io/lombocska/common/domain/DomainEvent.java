//package io.lombocska.common.domain;
//
//import java.time.Clock;
//import java.time.Instant;
//import java.util.Optional;
//import java.util.UUID;
//
//public class DomainEvent<T> implements EventContract<T> {
//
//    private UUID id;
//
//    private String source;
//
//    private String type;
//
//    private String version;
//
//    private Instant created;
//
//    private UUID correlationId;
//
//    private String aggregateId;
//
//    private T payload;
//
//    protected DomainEvent(
//            UUID id,
//            String source,
//            UUID correlationId,
//            String aggregateId,
//            String type,
//            T payload) {
//        this(id, source, correlationId, aggregateId, type, payload, Clock.systemUTC());
//    }
//
//    protected DomainEvent(
//            UUID id,
//            String source,
//            UUID correlationId,
//            String aggregateId,
//            String type,
//            T payload,
//            Clock clock) {
//        this.id = requireNotNull(id, "id cannot be blank");
//        this.correlationId = null != correlationId ? correlationId : this.id;
//        this.type = requireNotBlank(type, "type cannot be blank");
//        this.source = requireNotBlank(source, "source cannot be blank");
//        this.created = Instant.now(requireNotNull(clock, "clock cannot be null"));
//        this.payload = requireNotNull(payload, "payload cannot be null");
//        this.aggregateId = aggregateId;
//        this.version = VERSION;
//    }
//
//    protected DomainEvent() {
//    }
//
//    @Override
//    public UUID getId() {
//        return id;
//    }
//
//    @Override
//    public String getSource() {
//        return source;
//    }
//
//    @Override
//    public String getVersion() {
//        return version;
//    }
//
//    @Override
//    public Instant getCreated() {
//        return created;
//    }
//
//    @Override
//    public UUID getCorrelationId() {
//        return correlationId;
//    }
//
//    @Override
//    public String getAggregateId() {
//        return aggregateId;
//    }
//
//    @Override
//    public String getType() {
//        return type;
//    }
//
//    @Override
//    public T getPayload() {
//        return payload;
//    }
//
//    private static String requireNotBlank(String validated, String message) {
//        if (null == validated || validated.isEmpty()) {
//            throw new IllegalArgumentException(message);
//        }
//        return validated;
//    }
//
//    private static <T> T requireNotNull(T validated, String message) {
//        if (null == validated) {
//            throw new IllegalArgumentException(message);
//        }
//        return validated;
//    }
//
//    public static <T> DomainEventBuilder<T> newBuilder() {
//        return new DomainEventBuilder<>();
//    }
//
//    public static final class DomainEventBuilder<T> {
//
//        private UUID id;
//
//        private UUID correlationId;
//
//        private String source;
//
//        private String type;
//
//        private String aggregateId;
//
//        private T payload;
//
//        private Clock clock;
//
//        private DomainEventBuilder() {
//        }
//
//        public DomainEventBuilder<T> withId(UUID id) {
//            this.id = id;
//            return this;
//        }
//
//        public DomainEventBuilder<T> withSource(String source) {
//            this.source = source;
//            return this;
//        }
//
//        public DomainEventBuilder<T> withType(String type) {
//            this.type = type;
//            return this;
//        }
//
//        public DomainEventBuilder<T> withCorrelationId(UUID correlationId) {
//            this.correlationId = correlationId;
//            return this;
//        }
//
//        public DomainEventBuilder<T> withAggregateId(String aggregateId) {
//            this.aggregateId = aggregateId;
//            return this;
//        }
//
//        public DomainEventBuilder<T> withPayload(T payload) {
//            this.payload = payload;
//            return this;
//        }
//
//        public DomainEventBuilder<T> withClock(Clock clock) {
//            this.clock = clock;
//            return this;
//        }
//
//        public DomainEvent<T> build() {
//            return new DomainEvent<>(id, source, correlationId, aggregateId, type, payload,
//                    Optional.ofNullable(clock).orElse(Clock.systemUTC()));
//        }
//
//    }
//
//    @Override
//    public String toString() {
//        return "DomainEvent{" +
//                "id=" + id +
//                ", source='" + source + '\'' +
//                ", type='" + type + '\'' +
//                ", version='" + version + '\'' +
//                ", created=" + created +
//                ", correlationId=" + correlationId +
//                ", aggregateId='" + aggregateId + '\'' +
//                ", payload=" + payload +
//                '}';
//    }
//
//}
