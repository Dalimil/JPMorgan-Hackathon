"""empty message

Revision ID: 413049c3c46f
Revises: 5313b2c16427
Create Date: 2015-11-07 22:03:30.819102

"""

# revision identifiers, used by Alembic.
revision = '413049c3c46f'
down_revision = '5313b2c16427'

from alembic import op
import sqlalchemy as sa


def upgrade():
    ### commands auto generated by Alembic - please adjust! ###
    op.add_column('projects', sa.Column('lat', sa.Float(), nullable=True))
    op.add_column('projects', sa.Column('lng', sa.Float(), nullable=True))
    op.add_column('users', sa.Column('lat', sa.Float(), nullable=True))
    op.add_column('users', sa.Column('lng', sa.Float(), nullable=True))
    ### end Alembic commands ###


def downgrade():
    ### commands auto generated by Alembic - please adjust! ###
    op.drop_column('users', 'lng')
    op.drop_column('users', 'lat')
    op.drop_column('projects', 'lng')
    op.drop_column('projects', 'lat')
    ### end Alembic commands ###