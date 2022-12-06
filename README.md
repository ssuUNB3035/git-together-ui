# Welcome to Git-Together!
### Team Members: Samuel Su, Conan Simons
Git-Together is a collaborative platform, aiming to help students and indie developers find team members for their projects. The following information is a breakdown of Git-Together's features and the current list of bugs and to-do(s).

## Known Bugs and Missing Features
- The login screen is not finalized. Instead, a dummy account is provided once the application is launched.
- The user list on the project page has not been finalized.
- The Filter button on the homepage does not work correctly. Filtering with no tags brings back all the unselected project cards, but filtering via tags will not show projects if there aren't any cards showing at the time of filtering.

## Homepage
The Homepage is the first screen you are brought to when launching the application. Once brought to this page you are provided with three dummy project cards and the projects created in the database.

On the Homepage, you may swipe left on the project card to decline to join the said project. Swiping right on the project card will enroll you into the project and will show on the "manage projects" page.

Users may filter the projects by predetermined tags via the button on the top right of the Homepage. On the bottom of the page, users can click on "Edit Profile", "Manage Projects", and "Create Projects" buttons to redirect themselves to the corresponding pages.

## Edit Profile
The Edit Profile page allows users to change their profile information. Once entering this page, all of the fields will be prepopulated with their user data. Once the "save" button is clicked, the user data is then updated in the database.

## Manage Projects
The "Manage Projects" page includes all of the projects the user has "swiped right on". These projects can be individually viewed when clicked, in addition to being removed from the manage projects page once the "remove" button has been clicked in the corresponding row.

Users may also enroll themselves into projects by clicking on the "Add Projects" button on the top right of the "Manage Projects" page. This button will open a dialog box where the user can enter a shareable hash given by invite emails sent from team members of the project.

## Create Project
Once users enter the "Create Project" page, they are presented with multiple fields required to create a project. Once these fields are filled and the user clicks on the "create" button, the project is sent to the database and automatically added to the "Manage Projects" page.
