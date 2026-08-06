Nếu theo hướng **REST API Backend** thì nên chia theo từng module, mỗi module có 1 endpoint chính. Sau này frontend gọi rất dễ.

---

# 1. Dashboard API

> Màn hình đầu tiên của Group Admin.

```http
GET /api/v1/groups/{groupId}/dashboard
```

Response

```json
{
  "totalMembers": 35,
  "activeMembers": 28,
  "completedMembers": 12,
  "averageProgress": 64.5,
  "totalCourses": 8,
  "totalAssignments": 16,
  "totalQuizzes": 22
}
```

---

# 2. Member Progress API

Lấy danh sách thành viên trong group.

```http
GET /api/v1/groups/{groupId}/members
```

Có phân trang

```
?page=1
&size=10
&keyword=james
&sort=progress,desc
```

Response

```json
{
  "data":[
    {
      "userId":1,
      "fullName":"James",
      "email":"...",
      "progress":82,
      "completedCourses":5,
      "lastLearning":"2026-08-04T20:30",
      "status":"ACTIVE"
    }
  ]
}
```

---

# 3. Member Detail API

Click vào 1 member.

```http
GET /api/v1/groups/{groupId}/members/{userId}
```

Response

```json
{
    "userId":1,
    "fullName":"James",

    "courses":[
        {
            "courseId":5,
            "courseName":"Spring Boot",
            "progress":75,
            "lastVideo":"Authentication",
            "lastLearning":"..."
        }
    ]
}
```

---

# 4. Quiz Result API

Xem kết quả quiz của cả nhóm.

```http
GET /api/v1/groups/{groupId}/quizzes
```

filter

```
?courseId=
?page=
&size=
```

Response

```json
{
    "data":[
        {
            "userId":1,
            "fullName":"James",
            "quizName":"Chapter 1",
            "score":92,
            "attempt":2,
            "passed":true,
            "submittedAt":"..."
        }
    ]
}
```

---

# 5. Assignment API

```http
GET /api/v1/groups/{groupId}/assignments
```

Response

```json
{
    "data":[
        {
            "assignmentId":10,
            "title":"ER Diagram",

            "submitted":18,
            "notSubmitted":12,

            "deadline":"..."
        }
    ]
}
```

---

# 6. Assignment Detail API

Click vào assignment.

```http
GET /api/v1/groups/{groupId}/assignments/{assignmentId}
```

Response

```json
{
    "submittedUsers":[...],
    "notSubmittedUsers":[...]
}
```

---

# 7. Learning Activity API

Activity feed.

```http
GET /api/v1/groups/{groupId}/activities
```

Response

```json
{
    "data":[
        {
            "user":"James",
            "action":"WATCH_VIDEO",
            "target":"Spring Security #4",
            "time":"..."
        },
        {
            "user":"Anna",
            "action":"FINISHED_QUIZ",
            "target":"Quiz 2",
            "score":96
        }
    ]
}
```

---

# 8. Inactive Members API

```http
GET /api/v1/groups/{groupId}/inactive-members
```

filter

```
?days=7
```

Response

```json
{
    "data":[
        {
            "userId":5,
            "name":"John",
            "lastLearning":"2026-07-25",
            "progress":15
        }
    ]
}
```

---

# 9. Leaderboard API

```http
GET /api/v1/groups/{groupId}/leaderboard
```

Response

```json
{
    "topProgress":[...],
    "topQuiz":[...],
    "topLearningTime":[...]
}
```

---

## Nếu theo kiến trúc microservice thì mình sẽ tách như sau:

| API                 | Service phụ trách                                    |
| ------------------- | ---------------------------------------------------- |
| `/dashboard`        | Group Service (tổng hợp dữ liệu từ các service khác) |
| `/members`          | Group Service                                        |
| `/members/{id}`     | Learning Progress Service                            |
| `/quizzes`          | Test Service                                         |
| `/assignments`      | Assignment Service                                   |
| `/activities`       | Learning Progress Service (ghi nhận log học tập)     |
| `/inactive-members` | Learning Progress Service                            |
| `/leaderboard`      | Analytics hoặc Learning Progress Service             |

Như vậy, **Group Service** chỉ đóng vai trò điều phối (orchestrator), còn dữ liệu chi tiết vẫn do các service chuyên trách quản lý. Cách này dễ mở rộng và phù hợp với mô hình LMS dùng microservices.
