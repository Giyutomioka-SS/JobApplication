# JobApplication (Lokal Android Assignment)

**JobApplication** is an Android application designed as part of an intern assignment. The app allows users to explore job listings, bookmark their favorite jobs, and view bookmarked jobs offline.

## Short Description

### Jobs Screen:
- Fetches job data from the endpoint: [https://testapi.getlokalapp.com/common/jobs?page=1](https://testapi.getlokalapp.com/common/jobs?page=1) using an infinite scroll approach.
- Displays job details including title, location, salary, and phone number on each card.
- Clicking on a job card navigates to a detailed view of the job.

### Bookmarks Screen:
- Users can bookmark jobs, which will then appear in the "Bookmarks" tab.
- Bookmarked jobs are stored in a local database for offline access.

### Bottom Navigation UI: 
- The app features a bottom navigation with two main sections: "Jobs" and "Bookmarks."
  
### State Management: 
- The app maintains appropriate states for loading, error, and empty scenarios throughout the application.

## Key Features

- **MVVM Architecture**: Separation of UI, business logic, and data handling to ensure a clean and manageable codebase.
- **Room Database**: Local storage for bookmarked jobs, enabling offline access and persistence.
- **Bottom Navigation**: Smooth navigation between the "Jobs" and "Bookmarks" sections.
- **Job Listing**: Each job card includes detailed information such as title, location, salary, and phone number.
- **Job Details**: View detailed information for each job by clicking on a job card.
- **Bookmarking**: Save jobs locally for offline viewing, ensuring accessibility even without an internet connection.
- **State Management**: Effectively handles different states such as loading, error, and empty to provide a seamless user experience.
