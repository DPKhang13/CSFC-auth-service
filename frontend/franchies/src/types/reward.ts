export interface Reward {
  id: number;
  name: string;
  description: string;
  pointsCost: number;
  category: string;
  imageUrl: string;
  inStock: boolean;
  featured?: boolean;
}

export const RewardCategory = {
  ALL: 'All Rewards',
  FOOD_DRINK: 'Food & Drink',
  RETAIL: 'Retail Items',
  EXPERIENCES: 'Experiences',
  GIFT_CARDS: 'Gift Cards'
} as const;

export type RewardCategoryType = typeof RewardCategory[keyof typeof RewardCategory];

export interface CustomerLoyalty {
  pointsAvailable: number;
  currentTier: string;
  pointsToNextReward: number;
}

export interface PointRange {
  label: string;
  min: number;
  max?: number;
}
