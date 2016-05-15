# Grid Image Search (Google API)

> What's Grid Image Search?

This is a Google Image Search app which allows an user to select search filters and paginate results infinitely.

> What can you do?

You can enter a search query that will display a grid of image results from the Google Image API.

> What are the additional features?

* User can configure advanced search filters such as:
  - Size (small, medium, large, extra-large)
  - Color(blac k, blue, brown, gray, green, etc...)
  - Type (faces, photo, clip art, line art)
  - Site (espn.com)
* Subsequent searches will have any filters applied to the search results.
* User can tap on any image in results to see the image full-screen
* User can scroll down “infinitely” to continue loading more image results (up to 10 pages or 100 images).
* Robust error handling, check if internet is available, handle error cases, network failures

## Prerequisites
#### Search engine ID
  
  By calling the API user issues requests against an existing instance of a Custom Search Engine. Therefore, before using the API, you need to create one in the Control Panel. Follow the tutorial to learn more about different configuration options. You can find the engine's ID in the Setup > Basics > Details section of the Control Panel.
  
#### API Key
  
  JSON/Atom Custom Search API requires the use of an API key.
  
  * Custom Search Engine (free edition) users can obtain the key from the Google Developers Console.
  * Google Site Search (paid edition) users can find their API key in the Control Panel in Business > XML & JSON tab.

## Pricing
  * For [Custom Search Engine](https://cse.google.com/cse/) (FREE) users, the API provides 100 search queries per day for free. If you need more, you may sign up for billing in the Developers Console. Additional requests cost $5 per 1000 queries, up to 10k queries per day.
  * For [Google Site Search](https://www.google.com/work/search/products/gss.html) (Paid) users, usage limits and quotas, please check [pricing options](https://www.google.com/work/search/products/gss.html#pricing_content).
   

## Walkthrough

## License
 * [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

## Acknowledgements
 * [Google Custom Search](https://developers.google.com/custom-search/)

GIF created with [LiceCap](http://www.cockos.com/licecap/).