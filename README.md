# Welcome to Git-Together!
### Team Members: Samuel Su, Conan Simons
Git-Together is a collaborative platform, aiming to help students and indie developers find team members for their projects. The following information is a breakdown of Git-Together's features and the current list of bugs and to-do(s).

## Completed Features

- Swipe right on projects you're interested in! It will be added to your 'Manage Projects' page
- Swiping left on projects will remove them from your view.
- Users have a profile, which can be edited through the 'Edit Profile' page. They can edit their Name, Bio, Location, and Email.
- The projects that a user is in will be listed under 'Manage Projects'. For now,  as long as you're interested you will get access.
- Users in projects can invite their friends! There is a invite memebers button under the Projects page. This will send an email to an email of your choosing, with an invitation code.
- Interested users can also gain access to projects by entering the invite code. On the 'Manage Projects' page, there will be a button on the top right. Once prompted, enter the invite code to be added.
- Users can create projects. Projects have a Name, Description, Location, Link, along with any tags you would like to add.

## Known Bugs and Missing Features
- The login screen is not finalized. Instead, a dummy account is provided once the application is launched.
- The user list on the project page has not been finalized.
- The Filter button on the homepage does not work correctly. Filtering with no tags brings back all the unselected project cards, but filtering via tags will not show projects if there aren't any cards showing at the time of filtering. Does not filter by location.
- Projects do not have an owner, as a result, no one to accept interested users.

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

## Testing Steps
1.Test if you can swipe left and right on projects on the Homepage. Projects that
  are swiped right on should be added to the Manage Projects Page. Projects that
  are swiped left on should be discarded.
2.From the Manage Projects page, you should be able to click on projects that
  you’ve swiped right on, in addition to projects you have created on the Create
  Projects Page.
3.Go to the Create Project page and create a project with all of the fields filled, if all
  of the fields are not filled then you cannot create the project. Enter any URL for
  the link.
4.The created project should be on the Manage Projects Page. Go to the Project
  page for the Project you just created and tap on the repository link, you should be
  redirected to the link in a browser on your device.
5.You can also add members to Projects you are a part of via the Invite button at the top right of
  the Project page and enter an email to send an invite hash code. (This has not been successfully
  tested yet)
6.In the Manage Projects page, remove all of the projects. Then tap on the “Add
  Project” button on the top right, and enter this hash:
  4e48139d95f37e7d97e295b03a5d9cf6
7.you should see a project added to your Manage Projects page. If not, restart the app and 
  re-enter the hash.