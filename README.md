# Grid Image Search (Google)

This is a Google Image Search app which allows a user to select search filters and paginate results infinitely.  

Time spent: 2 hours spent in total

The following user stories must be completed:

 * [ ] Required: User can enter a search query that will display a grid of image results from the Google Image API.
 * [ ] Required: User can click on "settings" which allows selection of advanced search options to filter results
 * [ ] Required: User can configure advanced search filters such as: <br />
    - [ ] Required: Size (small, medium, large, extra-large)
    - [ ] Required: Color filter (black, blue, brown, gray, green, etc...)
    - [ ] Required: Type (faces, photo, clip art, line art)
    - [ ] Required: Site (espn.com)
 * [ ] Required: Subsequent searches will have any filters applied to the search results
 * [ ] Required: User can tap on any image in results to see the image full-screen
 * [ ] Required: User can scroll down “infinitely” to continue loading more image results (up to 8 pages)
 * [ ] Required: The following advanced user stories are optional:
 * [ ] Advanced: Robust error handling, check if internet is available, handle error cases, network failures
 * [ ] Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
 * [ ] Advanced: User can share an image to their friends or email it to themselves
 * [ ] Advanced: Replace Filter Settings Activity with a lightweight modal overlay
 * [ ] Advanced: Improve the user interface and experiment with image assets and/or styling and coloring
 * [ ] Bonus: Use the StaggeredGridView to display improve the grid of image results
 * [ ] Bonus: User can zoom or pan images displayed in full-screen detail view
Notes:

Spent some time making the UI work across multiple phone resolutions by playing around with the RelativeLayout.

Walkthrough of all user stories:

![Video Walkthrough](GoogleImageSearch.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).