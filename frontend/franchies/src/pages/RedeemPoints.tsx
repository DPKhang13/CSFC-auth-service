import { useState } from 'react';
import RewardCard from '../components/RewardCard';
import CategoryFilter from '../components/CategoryFilter';
import RedemptionConfirmModal from '../components/RedemptionConfirmModal';
import RedemptionSuccessModal from '../components/RedemptionSuccessModal';
import { RewardCategory } from '../types/reward';
import type { Reward, CustomerLoyalty, PointRange } from '../types/reward';
import './RedeemPoints.css';

// Mock data - trong thực tế sẽ fetch từ API
const mockRewards: Reward[] = [
  {
    id: 1,
    name: 'Premium Roast Coffee',
    description: 'Enjoy a free large premium roast coffee at any participating location.',
    pointsCost: 450,
    category: RewardCategory.FOOD_DRINK as string,
    imageUrl: 'https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400&h=300&fit=crop',
    inStock: true,
  },
  {
    id: 2,
    name: 'Exclusive Merchandise Pack',
    description: 'Get our limited edition branded merchandise pack including a tote...',
    pointsCost: 2500,
    category: RewardCategory.RETAIL as string,
    imageUrl: 'https://images.unsplash.com/photo-1513885535751-8b9238bd345a?w=400&h=300&fit=crop',
    inStock: true,
  },
  {
    id: 3,
    name: 'VIP Event Access Pass',
    description: 'Enjoy entry to local cultural events and grand openings for you a...',
    pointsCost: 5000,
    category: RewardCategory.EXPERIENCES as string,
    imageUrl: 'https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?w=400&h=300&fit=crop',
    inStock: true,
  },
  {
    id: 4,
    name: '$50 Digital Gift Card',
    description: 'A $50 gift card delivered to your email. Valid for online and in-sto...',
    pointsCost: 4800,
    category: RewardCategory.GIFT_CARDS as string,
    imageUrl: 'https://images.unsplash.com/photo-1607083206869-4c7672e72a8a?w=400&h=300&fit=crop',
    inStock: true,
  },
  {
    id: 5,
    name: 'Family Feast Bundle',
    description: 'A complete meal for 4 - perfect for 2 sides. Perfect for a family night in...',
    pointsCost: 3200,
    category: RewardCategory.FOOD_DRINK as string,
    imageUrl: 'https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400&h=300&fit=crop',
    inStock: true,
  },
  {
    id: 6,
    name: '20% Off Next Order',
    description: 'Get an automatically 20% discount applied to your next purchase over $50.',
    pointsCost: 1000,
    category: RewardCategory.RETAIL as string,
    imageUrl: 'https://images.unsplash.com/photo-1607083206325-caf1edba7a0f?w=400&h=300&fit=crop',
    inStock: false,
  },
];

const mockCustomerLoyalty: CustomerLoyalty = {
  pointsAvailable: 24550,
  currentTier: 'Platinum Member',
  pointsToNextReward: 40,
};

const pointRanges: PointRange[] = [
  { label: 'Under 500 pts', min: 0, max: 500 },
  { label: '500 - 2,000 pts', min: 500, max: 2000 },
  { label: '2,000+ pts', min: 2000 },
];

const RedeemPoints = () => {
  const [selectedCategory, setSelectedCategory] = useState<string>(RewardCategory.ALL);
  const [selectedPointRange, setSelectedPointRange] = useState<PointRange | null>(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [sortBy, setSortBy] = useState('Recommended');
  
  // Modal states
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [selectedReward, setSelectedReward] = useState<Reward | null>(null);
  const [currentBalance, setCurrentBalance] = useState(mockCustomerLoyalty.pointsAvailable);
  const [transactionId, setTransactionId] = useState('');

  const itemsPerPage = 6;

  // Lọc rewards dựa vào category, point range và search query
  const filteredRewards = mockRewards.filter((reward) => {
    const matchesCategory = 
      selectedCategory === (RewardCategory.ALL as string) || reward.category === selectedCategory;
    
    const matchesPointRange = !selectedPointRange || 
      (reward.pointsCost >= selectedPointRange.min && 
       (!selectedPointRange.max || reward.pointsCost <= selectedPointRange.max));
    
    const matchesSearch = !searchQuery || 
      reward.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      reward.description.toLowerCase().includes(searchQuery.toLowerCase());

    return matchesCategory && matchesPointRange && matchesSearch;
  });

  // Đếm số lượng rewards theo category
  const categoryCounts = [
    { category: RewardCategory.ALL as string, count: mockRewards.length },
    { category: RewardCategory.FOOD_DRINK as string, count: mockRewards.filter(r => r.category === (RewardCategory.FOOD_DRINK as string)).length },
    { category: RewardCategory.RETAIL as string, count: mockRewards.filter(r => r.category === (RewardCategory.RETAIL as string)).length },
    { category: RewardCategory.EXPERIENCES as string, count: mockRewards.filter(r => r.category === (RewardCategory.EXPERIENCES as string)).length },
    { category: RewardCategory.GIFT_CARDS as string, count: mockRewards.filter(r => r.category === (RewardCategory.GIFT_CARDS as string)).length },
  ];

  // Phân trang
  const totalPages = Math.ceil(filteredRewards.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const paginatedRewards = filteredRewards.slice(startIndex, startIndex + itemsPerPage);

  const handleRedeem = (reward: Reward) => {
    setSelectedReward(reward);
    setShowConfirmModal(true);
  };

  const handleConfirmRedemption = () => {
    if (selectedReward && currentBalance >= selectedReward.pointsCost) {
      // Generate transaction ID
      const txnId = `#TXN-${Math.floor(Math.random() * 9000) + 1000}-${Math.random().toString(36).substr(2, 2).toUpperCase()}`;
      setTransactionId(txnId);
      
      // Deduct points
      const newBalance = currentBalance - selectedReward.pointsCost;
      setCurrentBalance(newBalance);
      
      // Close confirm modal and open success modal
      setShowConfirmModal(false);
      setShowSuccessModal(true);
      
      // In production: call API to process redemption
    }
  };

  const handleCancelRedemption = () => {
    setShowConfirmModal(false);
    setSelectedReward(null);
  };

  const handleViewHistory = () => {
    setShowSuccessModal(false);
    // Navigate to history page
    alert('Navigating to History page...');
  };

  const handleBackToRewards = () => {
    setShowSuccessModal(false);
    setSelectedReward(null);
  };

  return (
    <div className="redeem-points-page">
      {/* Header */}
      <header className="page-header">
        <div className="logo">
          {/* <span className="logo-icon">💎</span> */}
          <span className="logo-text">FPT Rewards</span>
        </div>
        <nav className="main-nav">
          <a href="#" className="nav-link">Dashboard</a>
          <a href="#" className="nav-link active">Redeem Points</a>
          <a href="#" className="nav-link">History</a>
        </nav>
        <div className="user-info">
          <span className="welcome-text">Welcome back</span>
          <span className="user-name">Alexandra Doe</span>
          <div className="user-avatar">A</div>
        </div>
      </header>

      <div className="page-content">
        {/* Loyalty Balance Section */}
        <div className="loyalty-balance-section">
          <div className="balance-card">
            <div className="balance-info">
              <h2 className="balance-label">LOYALTY BALANCE</h2>
              <div className="balance-amount">
                {currentBalance.toLocaleString()}{' '}
                <span className="points-label">Points Available</span>
              </div>
              {/* <p className="balance-note">
                <span className="check-icon">✓</span>
                Valid across all franchise locations globally.
              </p> */}
            </div>
            <div className="tier-badge">
              <div className="tier-icon">👑</div>
              <div className="tier-info">
                <span className="tier-status">CURRENT TIER</span>
                <span className="tier-name">{mockCustomerLoyalty.currentTier}</span>
                <span className="tier-progress">
                  Next Reward<br />
                  <strong>In {mockCustomerLoyalty.pointsToNextReward} pts</strong>
                </span>
              </div>
            </div>
          </div>
        </div>

        {/* Main Content */}
        <div className="main-content">
          {/* Sidebar Filter */}
          <aside className="sidebar">
            <CategoryFilter
              categories={categoryCounts}
              selectedCategory={selectedCategory}
              onCategoryChange={setSelectedCategory}
              pointRanges={pointRanges}
              selectedPointRange={selectedPointRange}
              onPointRangeChange={setSelectedPointRange}
            />
          </aside>

          {/* Rewards Grid */}
          <div className="rewards-section">
            {/* Search and Sort Bar */}
            <div className="controls-bar">
              <div className="search-box">
                <span className="search-icon">🔍</span>
                <input
                  type="text"
                  placeholder="Search rewards..."
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                  className="search-input"
                />
              </div>
              <div className="sort-controls">
                <label className="sort-label">Sort by:</label>
                <select 
                  value={sortBy} 
                  onChange={(e) => setSortBy(e.target.value)}
                  className="sort-select"
                >
                  <option>Recommended</option>
                  <option>Points: Low to High</option>
                  <option>Points: High to Low</option>
                  <option>Newest First</option>
                </select>
              </div>
            </div>

            {/* Rewards Grid */}
            <div className="rewards-grid">
              {paginatedRewards.map((reward) => (
                <RewardCard key={reward.id} reward={reward} onRedeem={handleRedeem} />
              ))}
            </div>

            {/* Pagination */}
            {totalPages > 1 && (
              <div className="pagination">
                <button
                  className="page-btn"
                  onClick={() => setCurrentPage(Math.max(1, currentPage - 1))}
                  disabled={currentPage === 1}
                >
                  ‹
                </button>
                {Array.from({ length: totalPages }, (_, i) => i + 1).map((page) => (
                  <button
                    key={page}
                    className={`page-btn ${page === currentPage ? 'active' : ''}`}
                    onClick={() => setCurrentPage(page)}
                  >
                    {page}
                  </button>
                ))}
                <button
                  className="page-btn"
                  onClick={() => setCurrentPage(Math.min(totalPages, currentPage + 1))}
                  disabled={currentPage === totalPages}
                >
                  ›
                </button>
              </div>
            )}
          </div>
        </div>
      </div>

      {/* Footer */}
      <footer className="page-footer">
        <div className="footer-content">
          <div className="footer-brand">
            {/* <span className="logo-icon">💎</span> */}
            <span className="logo-text">FPT Rewards</span>
            <p className="footer-description">
              Rewarding loyal customers across our franchises globally. 
              Earn, redeem, and enjoy exclusive benefits.
            </p>
          </div>
          <div className="footer-links">
            <div className="footer-column">
              <h4>Support</h4>
              <a href="#">FAQ</a>
              <a href="#">Contact Us</a>
              <a href="#">Terms of Subscription</a>
            </div>
          </div>
        </div>
        <div className="footer-bottom">
          <p>© 2024 LuxeFranchise Group. All rights reserved.</p>
          <button className="theme-toggle">🌙</button>
        </div>
      </footer>

      {/* Modals */}
      {showConfirmModal && selectedReward && (
        <RedemptionConfirmModal
          reward={selectedReward}
          currentBalance={currentBalance}
          onConfirm={handleConfirmRedemption}
          onCancel={handleCancelRedemption}
        />
      )}

      {showSuccessModal && selectedReward && (
        <RedemptionSuccessModal
          reward={selectedReward}
          newBalance={currentBalance}
          transactionId={transactionId}
          onViewHistory={handleViewHistory}
          onBackToRewards={handleBackToRewards}
        />
      )}
    </div>
  );
};

export default RedeemPoints;
