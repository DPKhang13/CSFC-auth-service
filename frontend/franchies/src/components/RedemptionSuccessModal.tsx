import type { Reward } from '../types/reward';
import './RedemptionSuccessModal.css';

interface RedemptionSuccessModalProps {
  reward: Reward;
  newBalance: number;
  transactionId: string;
  onViewHistory: () => void;
  onBackToRewards: () => void;
}

const RedemptionSuccessModal = ({
  reward,
  newBalance,
  transactionId,
  onViewHistory,
  onBackToRewards
}: RedemptionSuccessModalProps) => {
  const currentDate = new Date();
  const formattedDate = currentDate.toLocaleDateString('en-US', { 
    month: 'short', 
    day: 'numeric', 
    year: 'numeric' 
  });
  const formattedTime = currentDate.toLocaleTimeString('en-US', { 
    hour: '2-digit', 
    minute: '2-digit',
    hour12: true 
  });

  const handleCopyTransactionId = () => {
    navigator.clipboard.writeText(transactionId);
    alert('Transaction ID copied to clipboard!');
  };

  return (
    <div className="modal-overlay" onClick={onBackToRewards}>
      <div className="modal-content redemption-success-modal" onClick={(e) => e.stopPropagation()}>
        {/* Success Header */}
        <div className="success-header">
          <div className="success-icon">
            <span className="trophy-icon">🏆</span>
          </div>
          <h2 className="success-title">Redemption Successful!</h2>
          <p className="success-message">
            You've successfully redeemed your points. Enjoy your reward!
          </p>
        </div>

        {/* Reward Card */}
        <div className="reward-success-card">
          <div className="reward-card-header">
            <span className="reward-card-badge">PREMIUM COFFEE REWARD</span>
            <div className="reward-card-subtitle">Valid at all franchise locations</div>
          </div>
          
          <div className="reward-card-qr">
            <div className="qr-code-box">
              {/* Simplified QR code representation */}
              <svg viewBox="0 0 100 100" className="qr-code-svg">
                <rect x="0" y="0" width="100" height="100" fill="white"/>
                <rect x="10" y="10" width="15" height="15" fill="black"/>
                <rect x="75" y="10" width="15" height="15" fill="black"/>
                <rect x="10" y="75" width="15" height="15" fill="black"/>
                <rect x="30" y="30" width="8" height="8" fill="black"/>
                <rect x="42" y="30" width="8" height="8" fill="black"/>
                <rect x="54" y="30" width="8" height="8" fill="black"/>
                <rect x="66" y="30" width="8" height="8" fill="black"/>
                <rect x="30" y="42" width="8" height="8" fill="black"/>
                <rect x="54" y="42" width="8" height="8" fill="black"/>
                <rect x="30" y="54" width="8" height="8" fill="black"/>
                <rect x="42" y="54" width="8" height="8" fill="black"/>
                <rect x="66" y="54" width="8" height="8" fill="black"/>
                <rect x="42" y="66" width="8" height="8" fill="black"/>
                <rect x="54" y="66" width="8" height="8" fill="black"/>
              </svg>
            </div>
          </div>

          <div className="reward-card-details">
            <div className="detail-row">
              <span className="detail-label">Points Deducted</span>
              <span className="detail-value negative">-{reward.pointsCost.toLocaleString()} pts</span>
            </div>
            <div className="detail-row">
              <span className="detail-label">New Balance</span>
              <span className="detail-value positive">{newBalance.toLocaleString()} pts</span>
            </div>
            <div className="detail-divider"></div>
            <div className="detail-row">
              <span className="detail-label">Date & Time</span>
              <span className="detail-value">{formattedDate} • {formattedTime}</span>
            </div>
          </div>
        </div>

        {/* Transaction ID */}
        <div className="transaction-id-section">
          <span className="transaction-label">TRANSACTION ID</span>
          <div className="transaction-id-box">
            <span className="transaction-id-value">{transactionId}</span>
            <button className="copy-btn" onClick={handleCopyTransactionId} title="Copy Transaction ID">
              📋
            </button>
          </div>
        </div>

        {/* Instructions */}
        <div className="redemption-instructions">
          <p className="instruction-text">
            Show me code to the cashier to claim your reward
          </p>
          <p className="expiry-notice">
            Expires in <strong>24 hours</strong> • Terms & Conditions apply
          </p>
        </div>

        {/* Actions */}
        <div className="success-actions">
          <button className="btn-view-history" onClick={onViewHistory}>
            <span className="btn-icon">🔄</span>
            View History
          </button>
          <button className="btn-back-rewards" onClick={onBackToRewards}>
            Back to Rewards
          </button>
        </div>
      </div>
    </div>
  );
};

export default RedemptionSuccessModal;
