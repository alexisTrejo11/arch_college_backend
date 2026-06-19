---
layers:
  # account-service
  - name: "Edge & clients"
    description: "Clients and other services call this microservice over HTTP."
    color: "#4c566a"
    expanded: true
    components:
      - "Admin consoles and future SPA"
      - "Other microservices (service-to-service REST)"
    responsibilities:
      - "Expose JWT-protected REST APIs"
    technologies:
      - "HTTPS"
      - "JWT Bearer tokens"

  - name: "Application runtime"
    description: "Account Service Spring Boot process."
    color: "#5e81ac"
    expanded: true
    components:
      - "Controllers (Auth, User)"
      - "Services & security configuration"
      - "JPA repositories"
    responsibilities:
      - "Identity and access for the Architecture College platform."
    technologies:
      - "Spring Web"
      - "Spring Data JPA"
      - "Spring Security"

  - name: "Data & platform integration"
    description: "Persistence and operational registration."
    color: "#a3be8c"
    expanded: true
    components:
      - "PostgreSQL account_db"
      - "Eureka client registration"
      - "Config Server client"
    responsibilities:
      - "Store users and credentials; register healthy instances"
    technologies:
      - "PostgreSQL 16"
      - "Spring Cloud Netflix Eureka"
      - "Spring Cloud Config"

  # curriculum-service
  - name: "Clients"
    description: "Admin tools, internal services (enrollment, schedule) calling the catalog over REST."
    color: "#4c566a"
    expanded: true
    components:
      - "Back-office UIs"
      - "Other microservices (Feign/RestTemplate/WebClient)"
    responsibilities:
      - "Consume stable catalog identifiers and relationships"
    technologies:
      - "HTTPS"
      - "Optional JWT from account-service"

  - name: "Application runtime"
    description: "Curriculum Service Spring Boot application."
    color: "#5e81ac"
    expanded: true
    components:
      - "REST controllers (career, area, professional line, subjects, series)"
      - "Domain services and validation"
      - "JPA repositories"
    responsibilities:
      - "Academic catalog source of truth for the platform"
    technologies:
      - "Spring Web"
      - "Spring Data JPA"
      - "Jakarta Validation"

  - name: "Persistence & platform"
    description: "Datastore and cloud-native hooks."
    color: "#a3be8c"
    expanded: true
    components:
      - "PostgreSQL curriculum_db"
      - "Eureka client"
      - "Config Server client"
    responsibilities:
      - "Persist normalized curriculum graph; register with discovery"
    technologies:
      - "PostgreSQL 16"
      - "Spring Cloud Netflix Eureka"
      - "Spring Cloud Config"

  # enrollment-service
  - name: "Clients"
    description: "Student portals, admins, and other services invoking enrollment flows."
    color: "#4c566a"
    expanded: true
    components:
      - "SPA / admin UI"
      - "Schedule, curriculum, student services (HTTP)"
    responsibilities:
      - "Submit enrollments, query state, trigger preloads"
    technologies:
      - "HTTPS"
      - "JWT (when secured)"

  - name: "Application runtime"
    description: "Enrollment Service Spring Boot application."
    color: "#5e81ac"
    expanded: true
    components:
      - "Enrollment command & finder controllers"
      - "Student enrollment controller"
      - "Preload controllers (student, subject, schedule, grade)"
      - "Domain services, repositories"
    responsibilities:
      - "Registration workflows bridging students and class groups"
    technologies:
      - "Spring Web"
      - "Spring Data JPA"
      - "Spring Data MongoDB"

  - name: "Persistence"
    description: "Transactional store plus document store for job state."
    color: "#a3be8c"
    expanded: true
    components:
      - "PostgreSQL enrollment_db"
      - "MongoDB (preload / projection collections)"
      - "Eureka + Config Server clients"
    responsibilities:
      - "ACID enrollments; durable preload status and blobs"
    technologies:
      - "PostgreSQL 16"
      - "MongoDB 7"
      - "Spring Cloud Netflix Eureka"
      - "Spring Cloud Config"

  # grade-service
  - name: "Edge & clients"
    description: "Admin tools, student and teacher apps, and peer services call Grade Service over HTTPS."
    color: "#4c566a"
    expanded: true
    components:
      - "Authenticated clients (JWT)"
      - "Internal microservice callers"
    responsibilities:
      - "Propagate bearer tokens issued by account-service"
    technologies:
      - "HTTPS"
      - "JWT"

  - name: "Application runtime"
    description: "Grade Service Spring Boot process"
    color: "#5e81ac"
    expanded: true
    components:
      - "Grade query/command controllers"
      - "Group and teacher grading controllers"
      - "Academic history controllers"
      - "Security, validation, rate limiting"
    responsibilities:
      - "Grades, groups, teacher workflows, student read models"
    technologies:
      - "Spring Web"
      - "Spring Data JPA"
      - "Spring Data MongoDB"
      - "Spring AMQP"

  - name: "Data & messaging"
    description: "PostgreSQL grade_db, MongoDB grade_db, RabbitMQ"
    color: "#a3be8c"
    expanded: true
    components:
      - "Relational grades and relationships"
      - "Document projections where used"
      - "AMQP exchanges/queues for history refresh"
    responsibilities:
      - "Durable grades; async integration"
    technologies:
      - "PostgreSQL 16"
      - "MongoDB 7"
      - "RabbitMQ 3"

  # schedule-service
  - name: "Clients"
    description: "Admin scheduling tools, enrollment service, and internal consumers."
    color: "#4c566a"
    expanded: true
    components:
      - "Back-office UI"
      - "Enrollment and grade services"
    responsibilities:
      - "Create and query offerings; mutate rosters"
    technologies:
      - "HTTPS"
      - "JWT when secured"

  - name: "Application runtime"
    description: "Schedule Service Spring Boot application."
    color: "#5e81ac"
    expanded: true
    components:
      - "GroupCreationController"
      - "GroupFinderController"
      - "GroupUpdateController"
      - "GroupDeletionController"
      - "Domain services and JPA"
    responsibilities:
      - "Operational timetable and capacity management"
    technologies:
      - "Spring Web"
      - "Spring Data JPA"
      - "Spring AMQP"

  - name: "Data & messaging"
    description: "Relational state plus broker for integration."
    color: "#a3be8c"
    expanded: true
    components:
      - "PostgreSQL schedule_db"
      - "RabbitMQ"
      - "Eureka + Config Server clients"
    responsibilities:
      - "Durable groups; publish consumption-friendly events"
    technologies:
      - "PostgreSQL 16"
      - "RabbitMQ 3"
      - "Spring Cloud Netflix Eureka"
      - "Spring Cloud Config"

  # student-service
  - name: "Clients"
    description: "Admin portals, enrollment, grading, and other services."
    color: "#4c566a"
    expanded: true
    components:
      - "Internal REST clients"
      - "Future BFF / gateway"
    responsibilities:
      - "Read and mutate student records within auth policy"
    technologies:
      - "HTTPS"
      - "JWT when enforced"

  - name: "Application runtime"
    description: "Student Service Spring Boot application."
    color: "#5e81ac"
    expanded: true
    components:
      - "StudentCommandController"
      - "StudentQueryController"
      - "Services and JPA repositories"
    responsibilities:
      - "Student aggregate for the Architecture College platform"
    technologies:
      - "Spring Web"
      - "Spring Data JPA"
      - "Spring AMQP"

  - name: "Data & messaging"
    description: "Persistence plus broker."
    color: "#a3be8c"
    expanded: true
    components:
      - "PostgreSQL student_db"
      - "RabbitMQ"
      - "Eureka + Config Server clients"
    responsibilities:
      - "Durable students; publish consumption-friendly signals"
    technologies:
      - "PostgreSQL 16"
      - "RabbitMQ 3"
      - "Spring Cloud Netflix Eureka"
      - "Spring Cloud Config"

  # teacher-service
  - name: "Clients"
    description: "Schedule, grade, admin, and account-aligned callers."
    color: "#4c566a"
    expanded: true
    components:
      - "Internal microservices"
      - "Authenticated gateways"
    responsibilities:
      - "Manage faculty with JWT-protected routes"
    technologies:
      - "HTTPS"
      - "JWT Bearer"

  - name: "Application runtime"
    description: "Teacher Service Spring Boot application."
    color: "#5e81ac"
    expanded: true
    components:
      - "TeacherCommandController"
      - "TeacherQueryController"
      - "Security configuration"
      - "JPA repositories"
    responsibilities:
      - "Teacher aggregate backing scheduling and grading"
    technologies:
      - "Spring Web"
      - "Spring Data JPA"
      - "Spring Security"
      - "Spring AMQP"

  - name: "Data & messaging"
    description: "Relational store and broker."
    color: "#a3be8c"
    expanded: true
    components:
      - "PostgreSQL teacher_db"
      - "RabbitMQ"
      - "Eureka + Config Server clients"
    responsibilities:
      - "Persist teachers; integration events"
    technologies:
      - "PostgreSQL 16"
      - "RabbitMQ 3"
      - "Spring Cloud Netflix Eureka"
      - "Spring Cloud Config"

designPatterns:
  # account-service
  - title: "Database per service"
    emoji: "DB"
    description: "Account data lives only in account_db; no shared user tables with other services."
    category: "Microservices"
    badge: "Core"
  - title: "Token-based API security"
    emoji: "JWT"
    description: "Issue JWTs at login; other services validate the same signing key."
    category: "Security"
    badge: "Identity"

  # curriculum-service
  - title: "Database per service"
    emoji: "DB"
    description: "Only curriculum_db holds catalog tables; no cross-schema joins with enrollment or schedule."
    category: "Microservices"
    badge: "Core"
  - title: "Aggregate-oriented APIs"
    emoji: "API"
    description: "One controller family per aggregate reduces coupling and keeps URLs predictable."
    category: "API"
    badge: "DDD-friendly"

  # enrollment-service
  - title: "Database per service"
    emoji: "DB"
    description: "enrollment_db schema is owned here; no foreign keys to other services' tables."
    category: "Microservices"
    badge: "Core"
  - title: "Polyglot persistence"
    emoji: "DOC"
    description: "OLTP in Postgres; Mongo for flexible preload documents and progress tracking."
    category: "Data"
    badge: "CQRS-flavored"

  # grade-service
  - title: "Database per service"
    emoji: "DB"
    description: "grade_db (SQL + document URI) owned by this service; no cross-service table sharing."
    category: "Microservices"
    badge: "Core"
  - title: "CQSP-style splits"
    emoji: "API"
    description: "Separate query and command controllers for grades reduce accidental coupling on writes."
    category: "API"
    badge: "Layering"

  # schedule-service
  - title: "Database per service"
    emoji: "DB"
    description: "schedule_db contains scheduling aggregates only."
    category: "Microservices"
    badge: "Core"
  - title: "Command/query split"
    emoji: "CQRS"
    description: "/v1/api/groups vs /v1/api/finder/groups separates writes from read-optimized queries."
    category: "API"
    badge: "Read model"

  # student-service
  - title: "Database per service"
    emoji: "DB"
    description: "student_db is owned only by this service."
    category: "Microservices"
    badge: "Core"
  - title: "CQRS-style HTTP split"
    emoji: "CQRS"
    description: "Separate controllers for commands and queries."
    category: "API"
    badge: "Separation"

  # teacher-service
  - title: "Database per service"
    emoji: "DB"
    description: "teacher_db owned solely by this service."
    category: "Microservices"
    badge: "Core"
  - title: "Strangler path normalization"
    emoji: "GW"
    description: "Gateway maps legacy /teachers reads to versioned public paths."
    category: "Integration"
    badge: "Gateway"

scalabilityStrategies:
  # account-service
  - title: "Stateless instances"
    description: "Scale out multiple Account Service replicas behind a load balancer; no server session affinity."
  - title: "Dedicated database"
    description: "Tune account_db independently from curriculum or enrollment workloads."

  # curriculum-service
  - title: "Stateless horizontal scale"
    description: "Read-heavy catalog scales with replica count and DB read tuning."
  - title: "Optional read caching later"
    description: "CDN or Redis for hot GET paths (e.g. full career trees) if metrics justify it."

  # enrollment-service
  - title: "Scale stateless API tier"
    description: "Horizontal replicas with sticky-less load balancing."
  - title: "Isolate bulk preload load"
    description: "Throttle or queue heavy preloads so OLTP enrollment latency stays predictable."

  # grade-service
  - title: "Stateless HTTP tier"
    description: "Scale Boot instances; JWT carries identity; no server session affinity required."
  - title: "Tune brokers and DB pools"
    description: "Rabbit consumers and JDBC pool sizes scale with traffic separately."

  # schedule-service
  - title: "Stateless API tier"
    description: "Scale replicas; offload heavy reporting with read replicas or cached projections if needed."
  - title: "Broker decoupling"
    description: "RabbitMQ absorbs peak fan-out to peers without synchronous chains."

  # student-service
  - title: "Scale stateless replicas"
    description: "Horizontal scaling behind load balancer."
  - title: "Tune broker consumers"
    description: "Prefetch and listener concurrency for peak registration periods."

  # teacher-service
  - title: "Stateless API replicas"
    description: "Scale JVMs; no session stickiness required."
  - title: "Rabbit consumer tuning"
    description: "Scale listeners or partition workloads by routing key if needed."

securityStrategies:
  # account-service
  - title: "JWT propagation"
    description: "This service mints tokens; consumers must validate signature, expiry, and roles."
  - title: "Secrets not in Git"
    description: "JWT secret and DB passwords come from environment / secret manager in real deployment."

  # curriculum-service
  - title: "Gateway + service auth"
    description: "Protect mutating routes in production; align with institutional RBAC."
  - title: "Least-privilege DB role"
    description: "DB user limited to curriculum_db only."

  # enrollment-service
  - title: "Student vs admin routes"
    description: "Different route families for self-service vs operator preload; enforce with Spring Security + gateway."
  - title: "Secrets for both datastores"
    description: "RDS/Atlas credentials via environment or secret manager — never committed."

  # grade-service
  - title: "JWT validation"
    description: "Resource server validates tokens; teacher/student routes derive account from the JWT."
  - title: "Role-gated admin routes"
    description: "Academic history admin endpoint uses `@PreAuthorize(\"hasRole('ADMIN')\")`."

  # schedule-service
  - title: "Role-aware mutations"
    description: "Restrict group mutations to operators; student-facing traffic should not hit dangerous routes."
  - title: "Least-privilege DB and broker creds"
    description: "Separate credentials per environment."

  # student-service
  - title: "Authenticated mutations"
    description: "Align write routes with Spring Security and gateway policies."
  - title: "PII handling"
    description: "Student data — minimize logging; encrypt at rest where required."

  # teacher-service
  - title: "Shared JWT signing key"
    description: "Must match account-service secret; never commit production values."
  - title: "Least privilege DB credentials"
    description: "teacher_db role only."

cacheStrategies:
  # account-service
  - name: "No default application cache"
    description: "Optional Redis if token blocklists or rate limiting are added later."
    ttl: "n/a"
    coverage: "None today"

  # curriculum-service
  - name: "Not enabled by default"
    description: "Add application or edge cache when profiling shows catalog hot spots."
    ttl: "TBD"
    coverage: "Future"

  # enrollment-service
  - name: "None by default"
    description: "Consider Redis for idempotency keys or rate limits on enrollment spikes."
    ttl: "n/a"
    coverage: "Future"

  # grade-service
  - name: "None by default"
    description: "Add Redis or CDN if read-heavy catalog or history endpoints need it."
    ttl: "n/a"
    coverage: "Future"

  # schedule-service
  - name: "Optional catalog cache"
    description: "Cache hot finder responses (e.g. current term slice) if metrics justify it."
    ttl: "TBD"
    coverage: "Future"

  # student-service
  - name: "Optional read cache"
    description: "Cache filter-heavy GET /by responses if profiling demands it."
    ttl: "TBD"
    coverage: "Future"

  # teacher-service
  - name: "None default"
    description: "Optional cache for validate-account hot path."
    ttl: "TBD"
    coverage: "Future"

architectureFeatures:
  # account-service
  - title: "Spring Cloud discovery"
    emoji: "Eureka"
    description: "Registers with Eureka for dynamic lookup."
  - title: "Externalized configuration"
    emoji: "Config"
    description: "Uses config-data/account-service.yml via Config Server."

  # curriculum-service
  - title: "Eureka registration"
    emoji: "EU"
    description: "Service instances advertise health to peers."
  - title: "Externalized YAML"
    emoji: "CFG"
    description: "Behavior tuned via config-data/curriculum-service.yml."

  # enrollment-service
  - title: "Discovery + external config"
    emoji: "EU"
    description: "Eureka registration; YAML from config-data/enrollment-service.yml."
  - title: "Bulk operation APIs"
    emoji: "JOB"
    description: "Preload controllers pattern for operational imports."

  # grade-service
  - title: "Spring Cloud discovery"
    emoji: "EU"
    description: "Eureka client registration from config."
  - title: "Centralized configuration"
    emoji: "CFG"
    description: "config-data/grade-service.yml via Config Server."

  # schedule-service
  - title: "Eureka + Config"
    emoji: "EU"
    description: "Discovery and externalized YAML from config-data/schedule-service.yml."
  - title: "AMQP integration"
    emoji: "MQ"
    description: "Spring AMQP templates/listeners as implemented in the module."

  # student-service
  - title: "Discovery + config"
    emoji: "EU"
    description: "Eureka + config-data/student-service.yml."
  - title: "AMQP integration"
    emoji: "MQ"
    description: "Spring AMQP for cross-service workflows."

  # teacher-service
  - title: "Discovery + config"
    emoji: "EU"
    description: "Eureka + config-data/teacher-service.yml."
  - title: "AMQP"
    emoji: "MQ"
    description: "Integration with peer services."

architectureDiagram:
  legendItems:
    # account-service
    - type: "service"
      label: "Microservice"
      color: "#5e81ac"
      icon: "hex"
    - type: "database"
      label: "Database"
      color: "#a3be8c"
      icon: "cylinder"
    # curriculum-service
    - type: "service"
      label: "Microservice"
      color: "#5e81ac"
      icon: "hex"
    - type: "database"
      label: "PostgreSQL"
      color: "#a3be8c"
      icon: "cylinder"
    # enrollment-service
    - type: "service"
      label: "Service"
      color: "#5e81ac"
      icon: "hex"
    - type: "database"
      label: "SQL"
      color: "#a3be8c"
      icon: "cylinder"
    - type: "database"
      label: "Mongo"
      color: "#d08770"
      icon: "leaf"
    # grade-service
    - type: "service"
      label: "Microservice"
      color: "#5e81ac"
      icon: "hex"
    - type: "database"
      label: "SQL / document"
      color: "#a3be8c"
      icon: "cylinder"
    - type: "queue"
      label: "Broker"
      color: "#ebcb8b"
      icon: "mail"
    # schedule-service
    - type: "service"
      label: "Microservice"
      color: "#5e81ac"
      icon: "hex"
    - type: "database"
      label: "PostgreSQL"
      color: "#a3be8c"
      icon: "cylinder"
    - type: "queue"
      label: "RabbitMQ"
      color: "#d08770"
      icon: "queue"
    # student-service
    - type: "service"
      label: "Service"
      color: "#5e81ac"
      icon: "hex"
    - type: "database"
      label: "PostgreSQL"
      color: "#a3be8c"
      icon: "cylinder"
    - type: "queue"
      label: "RabbitMQ"
      color: "#d08770"
      icon: "queue"
    # teacher-service
    - type: "service"
      label: "Service"
      color: "#5e81ac"
      icon: "hex"
    - type: "database"
      label: "PostgreSQL"
      color: "#a3be8c"
      icon: "cylinder"
    - type: "queue"
      label: "RabbitMQ"
      color: "#d08770"
      icon: "queue"
  nodes:
    # account-service
    - id: "client"
      label: "Clients"
      type: "client"
      x: 10
      y: 20
      status: "healthy"
      traffic: 100
    - id: "account_service"
      label: "Account Service"
      type: "service"
      x: 45
      y: 20
      status: "healthy"
      traffic: 80
    - id: "postgres"
      label: "PostgreSQL (account_db)"
      type: "database"
      x: 80
      y: 20
      status: "healthy"
    # curriculum-service
    - id: "client"
      label: "Clients"
      type: "client"
      x: 10
      y: 20
      status: "healthy"
      traffic: 100
    - id: "curriculum_service"
      label: "Curriculum Service"
      type: "service"
      x: 45
      y: 20
      status: "healthy"
      traffic: 85
    - id: "postgres"
      label: "PostgreSQL (curriculum_db)"
      type: "database"
      x: 80
      y: 20
      status: "healthy"
    # enrollment-service
    - id: "client"
      label: "Clients"
      type: "client"
      x: 8
      y: 25
      status: "healthy"
      traffic: 100
    - id: "enrollment"
      label: "Enrollment Service"
      type: "service"
      x: 38
      y: 25
      status: "healthy"
      traffic: 90
    - id: "pg"
      label: "PostgreSQL"
      type: "database"
      x: 72
      y: 18
      status: "healthy"
    - id: "mongo"
      label: "MongoDB"
      type: "database"
      x: 72
      y: 35
      status: "healthy"
    # grade-service
    - id: "client"
      label: "Clients"
      type: "client"
      x: 10
      y: 22
      status: "healthy"
      traffic: 100
    - id: "grade_service"
      label: "Grade Service"
      type: "service"
      x: 38
      y: 22
      status: "healthy"
      traffic: 85
    - id: "postgres"
      label: "PostgreSQL"
      type: "database"
      x: 68
      y: 12
      status: "healthy"
    - id: "mongodb"
      label: "MongoDB"
      type: "database"
      x: 68
      y: 28
      status: "healthy"
    - id: "rabbit"
      label: "RabbitMQ"
      type: "queue"
      x: 68
      y: 44
      status: "healthy"
    # schedule-service
    - id: "client"
      label: "Clients"
      type: "client"
      x: 8
      y: 28
      status: "healthy"
      traffic: 100
    - id: "schedule"
      label: "Schedule Service"
      type: "service"
      x: 36
      y: 28
      status: "healthy"
      traffic: 88
    - id: "pg"
      label: "PostgreSQL"
      type: "database"
      x: 68
      y: 20
      status: "healthy"
    - id: "mq"
      label: "RabbitMQ"
      type: "queue"
      x: 68
      y: 38
      status: "healthy"
    # student-service
    - id: "client"
      label: "Clients"
      type: "client"
      x: 8
      y: 28
      status: "healthy"
      traffic: 100
    - id: "student"
      label: "Student Service"
      type: "service"
      x: 36
      y: 28
      status: "healthy"
      traffic: 88
    - id: "pg"
      label: "PostgreSQL"
      type: "database"
      x: 68
      y: 20
      status: "healthy"
    - id: "mq"
      label: "RabbitMQ"
      type: "queue"
      x: 68
      y: 38
      status: "healthy"
    # teacher-service
    - id: "client"
      label: "Clients"
      type: "client"
      x: 8
      y: 28
      status: "healthy"
      traffic: 100
    - id: "teacher"
      label: "Teacher Service"
      type: "service"
      x: 36
      y: 28
      status: "healthy"
      traffic: 85
    - id: "pg"
      label: "PostgreSQL"
      type: "database"
      x: 68
      y: 20
      status: "healthy"
    - id: "mq"
      label: "RabbitMQ"
      type: "queue"
      x: 68
      y: 38
      status: "healthy"
  connections:
    # account-service
    - id: "c1"
      from: "client"
      to: "account_service"
      label: "REST"
      protocol: "HTTP"
      isActive: true
    - id: "c2"
      from: "account_service"
      to: "postgres"
      label: "SQL"
      protocol: "JDBC"
      isActive: true

    # curriculum-service
    - id: "c1"
      from: "client"
      to: "curriculum_service"
      label: "REST"
      protocol: "HTTP"
      isActive: true
    - id: "c2"
      from: "curriculum_service"
      to: "postgres"
      label: "SQL"
      protocol: "JDBC"
      isActive: true

    # enrollment-service
    - id: "c1"
      from: "client"
      to: "enrollment"
      label: "REST"
      protocol: "HTTP"
      isActive: true
    - id: "c2"
      from: "enrollment"
      to: "pg"
      label: "JDBC"
      protocol: "TCP"
      isActive: true
    - id: "c3"
      from: "enrollment"
      to: "mongo"
      label: "Mongo driver"
      protocol: "TCP"
      isActive: true

    # grade-service
    - id: "c1"
      from: "client"
      to: "grade_service"
      label: "REST"
      protocol: "HTTP"
      isActive: true
    - id: "c2"
      from: "grade_service"
      to: "postgres"
      label: "JDBC"
      protocol: "SQL"
      isActive: true
    - id: "c3"
      from: "grade_service"
      to: "mongodb"
      label: "ODM"
      protocol: "Mongo"
      isActive: true
    - id: "c4"
      from: "grade_service"
      to: "rabbit"
      label: "AMQP"
      protocol: "AMQP"
      isActive: true

    # schedule-service
    - id: "c1"
      from: "client"
      to: "schedule"
      label: "REST"
      protocol: "HTTP"
      isActive: true
    - id: "c2"
      from: "schedule"
      to: "pg"
      label: "JDBC"
      protocol: "TCP"
      isActive: true
    - id: "c3"
      from: "schedule"
      to: "mq"
      label: "AMQP"
      protocol: "TCP"
      isActive: true

    # student-service
    - id: "c1"
      from: "client"
      to: "student"
      label: "REST"
      protocol: "HTTP"
      isActive: true
    - id: "c2"
      from: "student"
      to: "pg"
      label: "JDBC"
      protocol: "TCP"
      isActive: true
    - id: "c3"
      from: "student"
      to: "mq"
      label: "AMQP"
      protocol: "TCP"
      isActive: true

    # teacher-service
    - id: "c1"
      from: "client"
      to: "teacher"
      label: "REST"
      protocol: "HTTP"
      isActive: true
    - id: "c2"
      from: "teacher"
      to: "pg"
      label: "JDBC"
      protocol: "TCP"
      isActive: true
    - id: "c3"
      from: "teacher"
      to: "mq"
      label: "AMQP"
      protocol: "TCP"
      isActive: true

dataFlow:
  requestFlow:
    # account-service
    - number: 1
      title: "Signup or login"
      description: "Client posts credentials or registration payload to /v1/api/auth/*."
      icon: "user"
    - number: 2
      title: "Validate and persist"
      description: "Service validates input, persists user, and for login returns JWT."
      icon: "database"
    - number: 3
      title: "Authenticated calls"
      description: "Clients pass JWT to this or other services for protected routes."
      icon: "shield"
    # curriculum-service
    - number: 1
      title: "Resolve catalog request"
      description: "Client calls GET/POST/PUT/DELETE on /v1/api/careers, /areas, /subjects/*, etc."
      icon: "globe"
    - number: 2
      title: "Validate and persist"
      description: "Controllers validate payloads and persist via JPA to curriculum_db."
      icon: "database"
    - number: 3
      title: "Downstream consumption"
      description: "Enrollment and schedule services use returned ids and keys in their own data."
      icon: "workflow"
    # enrollment-service
    - number: 1
      title: "Authorize request"
      description: "Gateway validates JWT/session before hitting enrollment APIs."
      icon: "shield"
    - number: 2
      title: "Execute use case"
      description: "Command or student controller validates business rules (capacity, lock date, duplicates)."
      icon: "globe"
    - number: 3
      title: "Persist"
      description: "Transactional writes to Postgres; preload metadata to Mongo when applicable."
      icon: "database"
    # grade-service
    - number: 1
      title: "Authenticate"
      description: "Client presents JWT from account-service where required."
      icon: "shield"
    - number: 2
      title: "Invoke API"
      description: "Controller validates input and delegates to domain services."
      icon: "globe"
    - number: 3
      title: "Persist / project"
      description: "Writes hit PostgreSQL; Mongo or messaging may update read models asynchronously."
      icon: "database"
    # schedule-service
    - number: 1
      title: "Invoke API"
      description: "Clients call creation, finder, or mutation endpoints under /v1/api/groups or /v1/api/finder/groups."
      icon: "globe"
    - number: 2
      title: "Validate and persist"
      description: "Controllers enforce rules; JPA commits to schedule_db."
      icon: "database"
    - number: 3
      title: "Integration signal"
      description: "Important changes may publish messages for enrollment or notifications."
      icon: "mail"
    # student-service
    - number: 1
      title: "REST call"
      description: "Clients hit /v1/api/students for reads or writes."
      icon: "globe"
    - number: 2
      title: "Business rules"
      description: "Validation, professional line rules, semester increments."
      icon: "check"
    - number: 3
      title: "Persist and notify"
      description: "JPA commit; optional message publish to Rabbit."
      icon: "database"
    # teacher-service
    - number: 1
      title: "Authenticate"
      description: "JWT validated for protected routes."
      icon: "shield"
    - number: 2
      title: "Invoke teacher API"
      description: "Commands /v1/api/teachers or queries /teachers."
      icon: "globe"
    - number: 3
      title: "Persist / integrate"
      description: "JPA to teacher_db; optional AMQP publish."
      icon: "database"
  eventFlow:
    # account-service
    - number: 1
      title: "No domain events in v1"
      description: "Account Service focuses on synchronous HTTP; optional outbox can be added later."
      icon: "mail"

    # curriculum-service
    - number: 1
      title: "Synchronous integration"
      description: "This service does not publish AMQP by default; peers integrate via REST."
      icon: "link"
    - number: 2
      title: "Future eventing"
      description: "Optional domain events (e.g. catalog change) can be added with an outbox pattern."
      icon: "mail"

    # enrollment-service
    - number: 1
      title: "Preload lifecycle"
      description: "POST /preload starts work; GET /preload/{id}/status polls Mongo-backed state."
      icon: "mail"
    - number: 2
      title: "Downstream visibility"
      description: "Schedule/grade services read enrollment facts via HTTP or shared events (if added later)."
      icon: "workflow"

    # grade-service
    - number: 1
      title: "Domain signal"
      description: "Grade or history changes may publish or consume AMQP messages."
      icon: "mail"
    - number: 2
      title: "Projection refresh"
      description: "Listeners update academic history or related aggregates."
      icon: "workflow"

    # schedule-service
    - number: 1
      title: "Publish"
      description: "AMQP messages emitted on schedule/group lifecycle events (as wired in code)."
      icon: "send"
    - number: 2
      title: "Consume"
      description: "Peer services update projections or trigger workflows."
      icon: "workflow"

    # student-service
    - number: 1
      title: "Emit"
      description: "Student lifecycle events on Rabbit (as wired)."
      icon: "send"
    - number: 2
      title: "Consume"
      description: "Peers refresh projections or trigger workflows."
      icon: "workflow"

    # teacher-service
    - number: 1
      title: "Teacher lifecycle signal"
      description: "Messages on create/delete or other events (per wiring)."
      icon: "send"
    - number: 2
      title: "Downstream reaction"
      description: "Schedule/grade adjust projections."
      icon: "workflow"

techDecisions:
  decisions:
    # account-service
    - title: "Spring Cloud Netflix (Eureka + Config)"
      problem: "Multiple JVM services need discovery and consistent configuration."
      solution: "Use Eureka registration and Spring Cloud Config with config-data YAML."
      outcome: "Operational parity between local Compose and target deployment."
      icon: "compass"
      alternatives:
        - "Kubernetes-only DNS + ConfigMaps"
        - "Consul for discovery and KV config"
    # curriculum-service
    - title: "Relational model for curriculum graph"
      problem: "Careers, areas, and subjects need constraints and joins for integrity."
      solution: "PostgreSQL + JPA entities with explicit relationships."
      outcome: "Enrollment and scheduling can trust foreign keys and uniqueness in curriculum_db."
      icon: "compass"
      alternatives:
        - "Document store for entire catalog tree"
        - "Graph database for prerequisite chains"
    # enrollment-service
    - title: "Postgres + Mongo in one service"
      problem: "Enrollments need ACID; bulk preload status needs flexible schema."
      solution: "JPA for enrollments; Mongo templates/repositories for process documents."
      outcome: "Operators get progress APIs without bloating relational tables."
      icon: "compass"
      alternatives:
        - "Single Postgres JSONB column for job state"
        - "External workflow engine (Temporal, Camunda)"
    # grade-service
    - title: "Dual store"
      problem: "Some reads suit documents while grades stay relational."
      solution: "PostgreSQL for grades; Mongo URI configured for grade_db projections."
      outcome: "Flexibility without giving up transactional grade rows."
      icon: "layer"
      alternatives:
        - "Postgres-only JSON columns for all projections"
        - "Event sourcing with separate read stack"
    - title: "Netflix Eureka + Config"
      problem: "Platform already standardizes on Spring Cloud Netflix."
      solution: "Reuse Eureka and Config Server for discovery and YAML."
      outcome: "Operational consistency across college microservices."
      icon: "compass"
      alternatives:
        - "Kubernetes service DNS only"
        - "Consul"
    # schedule-service
    - title: "Separate finder base path"
      problem: "Read-heavy schedule queries should not compete with transactional endpoints."
      solution: "Expose /v1/api/finder/groups for lookups and slices."
      outcome: "Clearer caching and scaling story for reads."
      icon: "compass"
      alternatives:
        - "Single controller with mixed GET/PUT"
        - "GraphQL facade over groups"
    # student-service
    - title: "Command/query controllers"
      problem: "Write models differ from read traffic patterns."
      solution: "StudentCommandController vs StudentQueryController."
      outcome: "Clearer evolution of validation and query optimization."
      icon: "compass"
      alternatives:
        - "Single controller class"
        - "GraphQL student API"
    # teacher-service
    - title: "Split command and query base paths"
      problem: "Legacy read API used /teachers without version prefix."
      solution: "TeacherCommandController under /v1/api/teachers; TeacherQueryController under /teachers."
      outcome: "Backward compatibility internally; gateway should unify externally."
      icon: "compass"
      alternatives:
        - "Refactor query controller to /v1/api/teachers (breaking change)"
        - "Single controller with explicit versioning headers"
---

# Project Architecture

> Auto-generated by `docs/project/merge_service_sources.py`. Edit service-level `{service}/docs/source/*.md` files, then regenerate.

<!-- BEGIN account-service -->
<!-- Source: account-service/docs/source/ProjectArchitecture.md -->
# Architecture

Narrative above aligns with **`ProjectArchitectureModel`** in `docs-schema.ts`.

## Watch items

- Rotate **JWT** signing keys per environment; never ship default keys.
- Narrow **Actuator** endpoint exposure in production (`management.endpoints.web.exposure`).
- Keep **password hashing** and validation rules in sync with institutional policy.

<!-- END account-service -->

<!-- BEGIN curriculum-service -->
<!-- Source: curriculum-service/docs/source/ProjectArchitecture.md -->
# Architecture

Aligns with **`ProjectArchitectureModel`** in `docs-schema.ts`.

## Watch items

- **`SubjectSeriesController`** uses `@RequestMapping("v1/api/...")` without a leading slash — align with `/v1/api/...` at the gateway.
- **`config-data/curriculum-service.yml`** may still reference a legacy `springdoc.packages-to-scan` value — confirm Swagger lists all controllers before release.
- Planning **migrations** (Flyway/Liquibase) is safer than relying on `ddl-auto` in non-dev environments.

<!-- END curriculum-service -->

<!-- BEGIN enrollment-service -->
<!-- Source: enrollment-service/docs/source/ProjectArchitecture.md -->
# Architecture

Matches **`ProjectArchitectureModel`** in `docs-schema.ts`.

## Watch items

- **Consistency:** document which reads are **Postgres-authoritative** vs **Mongo** (preload only).  
- **Path normalization:** several mappings omit a leading `/` — enforce **`/v1/api/...`** externally.  
- **migrate vs ddl-auto:** prefer **Flyway/Liquibase** on Postgres outside dev.  
- **Mongo indexes** on preload query fields (`processId`, TTL if ephemeral jobs).

<!-- END enrollment-service -->

<!-- BEGIN grade-service -->
<!-- Source: grade-service/docs/source/ProjectArchitecture.md -->
# Architecture

**Grade Service** is a Spring Boot 3.3 module with **Eureka**, **Config Server**, **PostgreSQL**, **MongoDB**, and **RabbitMQ** as in Compose.

## Watch items

- **`AcademicHistoryController`** maps to `v1/api/academic-histories` (no leading `/`). Treat externally as **`/v1/api/academic-histories`** after gateway normalization.
- **Ownership of read models:** clarify which store is authoritative per query path when troubleshooting discrepancies between SQL and Mongo-backed views.

<!-- END grade-service -->

<!-- BEGIN schedule-service -->
<!-- Source: schedule-service/docs/source/ProjectArchitecture.md -->
# Architecture

Matches **`ProjectArchitectureModel`** in `docs-schema.ts`.

## Watch items

- **Concurrency** on capacity and roster fields — use transactions/version columns if conflicts appear in production.  
- **RabbitMQ** topology (exchanges, queues, DLQs) should be version-controlled or declarative.  
- **ddl-auto** in non-dev — prefer **Flyway/Liquibase** for `schedule_db`.

<!-- END schedule-service -->

<!-- BEGIN student-service -->
<!-- Source: student-service/docs/source/ProjectArchitecture.md -->
# Architecture

Matches **`ProjectArchitectureModel`** in `docs-schema.ts`.

## Watch items

- **Idempotency** on progression endpoints if retried by clients.  
- **Rabbit** topology and dead-letter strategy for failed consumers.  
- **ddl-auto** vs migrations outside dev.

<!-- END student-service -->

<!-- BEGIN teacher-service -->
<!-- Source: teacher-service/docs/source/ProjectArchitecture.md -->
# Architecture

Matches **`ProjectArchitectureModel`** in `docs-schema.ts`.

## Watch items

- **Unify public paths** for Swagger and clients (`/v1/api/teachers` recommended).  
- **JWT key drift** between account-service and teacher-service causes **intermittent 401s**.  
- **Rabbit** topology as code; monitor DLQs.

<!-- END teacher-service -->
