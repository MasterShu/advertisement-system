package club.mastershu.ads.search;

import club.mastershu.ads.search.vo.SearchRequest;
import club.mastershu.ads.search.vo.SearchResponse;

public interface ISearch {
    SearchResponse fetchAds(SearchRequest request);
}
