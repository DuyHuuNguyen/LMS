# Auto Tagging Flow

## Overview

When a new course is created, the Backend saves it immediately and
triggers an asynchronous request to the AI Service. The AI analyzes the
course content, generates relevant tags, and calls the Backend callback
API. Finally, the Backend persists the generated tags and returns them
whenever the course information is requested.

## Flow

1.  User creates a course.
2.  Backend validates and saves the course.
3.  Backend returns **201 Created**.
4.  Backend sends an **asynchronous request** to the AI Service.
5.  AI Service analyzes the course content.
6.  AI Service generates tags.
7.  AI Service calls the Backend callback API with the generated tags.
8.  Backend saves the tags into the database.
9.  Frontend displays the tags when loading course details.

## Sequence Diagram

``` mermaid
sequenceDiagram
    actor User
    participant BE as Backend
    participant DB as Database
    participant AI as AI Service

    User->>BE: Create Course
    BE->>DB: Save Course
    DB-->>BE: Course Created

    BE-->>User: 201 Created

    BE->>AI: Async Request (Course ID + Content)
    AI->>AI: Generate Tags
    AI->>BE: Callback (Course ID + Tags)

    BE->>DB: Save Tags
    DB-->>BE: Success
```

## Architecture

``` text
User
 │
 │ Create Course
 ▼
Backend
 │
 ├── Save Course
 ▼
Database
 ▲
 │
 └────────────── Success
 │
 ├── Return 201 Created
 │
 └── Async Request
      ▼
   AI Service
      │
      ├── Generate Tags
      │
      └── Callback (Course ID + Tags)
              │
              ▼
          Backend
              │
              └── Save Tags
                      │
                      ▼
                  Database
``` 