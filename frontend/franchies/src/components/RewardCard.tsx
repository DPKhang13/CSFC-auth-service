import type { Reward } from '../types/reward';
import './RewardCard.css';

interface RewardCardProps {
  reward: Reward;
  onRedeem: (reward: Reward) => void;
}

const RewardCard = ({ reward, onRedeem }: RewardCardProps) => {
  const getCategoryBadgeClass = (category: string) => {
    switch (category) {
      case 'Food & Drink':
        return 'badge-food';
      case 'Retail Items':
        return 'badge-retail';
      case 'Experiences':
        return 'badge-experience';
      case 'Gift Cards':
        return 'badge-gift-card';
      default:
        return 'badge-default';
    }
  };

  return (
    <div className="reward-card">
      <div className="reward-image-container">
        <img src={reward.imageUrl} alt={reward.name} className="reward-image" />
        <span className={`category-badge ${getCategoryBadgeClass(reward.category)}`}>
          {reward.category === 'Food & Drink' ? 'Food & Drink' : 
           reward.category === 'Retail Items' ? 'Retail' :
           reward.category === 'Experiences' ? 'Experience' :
           reward.category === 'Gift Cards' ? 'Gift Cards' : reward.category}
        </span>
        {!reward.inStock && (
          <div className="out-of-stock-overlay">
            <span className="out-of-stock-badge">Out of Stock</span>
          </div>
        )}
      </div>
      <div className="reward-content">
        <h3 className="reward-name">{reward.name}</h3>
        <p className="reward-description">{reward.description}</p>
        <div className="reward-footer">
          <span className="reward-points">{reward.pointsCost.toLocaleString()} pts</span>
          <button 
            className={`redeem-button ${!reward.inStock ? 'disabled' : ''}`}
            onClick={() => onRedeem(reward)}
            disabled={!reward.inStock}
          >
            Redeem
          </button>
        </div>
      </div>
    </div>
  );
};

export default RewardCard;
