
package src.service.Review;

import src.service.IService;
import src.service.Review.Dtos.ReviewCreateDto;
import src.service.Review.Dtos.ReviewDto;
import src.service.Review.Dtos.ReviewUpdateDto;

public interface IReviewService extends IService<ReviewDto, ReviewCreateDto, ReviewUpdateDto> {
}
